package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.CommentEntity;
import com.win.server.entity.PostEntity;
import com.win.server.entity.RelationshipEntity;
import com.win.server.entity.UserEntity;
import com.win.server.exception.myexception.NotFoundException;
import com.win.server.repository.CommentRepository;
import com.win.server.repository.PostRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;
    private final RelationshipService relationshipService;
    private final UserService userService;
    public PostEntity createPost(String caption, MultipartFile media, String privacy) {
        PostEntity newPost = new PostEntity();
        newPost.setId(UUID.randomUUID().toString());
        newPost.setCreate_at(new Timestamp(System.currentTimeMillis()));
        String user_current = ContextUserManager.getUserId();
        newPost.setOwner_id(user_current);
        newPost.setInitiator_id(user_current);
        newPost.setCaption(caption);
        newPost.setPrivacy(privacy);
        //----- SAVE MEDIA FILE ------
        String fileId =  UUID.randomUUID().toString().replace("-","");
        String fileName = fileId + ".png";
        fileService.saveFile(media, fileName, "src/main/resources/public/image");
        String getImageURL = "/public/post?id=" + fileId;
        newPost.setImage(getImageURL);
        postRepository.savePost(newPost);
        return postRepository.findById(newPost.getId());
    }


    public List<PostEntity> getPostByUserId(String user_id) {
        List<PostEntity> listPost = postRepository.findByOwner(user_id);
        return filterVisiblePost(listPost);
    }

    public List<PostEntity> getPostByUsername(String username) {
        UserDTO user = userService.getUserByUsername(username);
        return getPostByUserId(user.getId());
    }


    public CommentEntity uploadComment(String post_id, String content) {
        PostEntity post = postRepository.findById(post_id);
        CommentEntity comment = new CommentEntity();
        comment.setId(UUID.randomUUID().toString());
        comment.setPost_id(post_id);
        comment.setUser_id(ContextUserManager.getUserId());
        comment.setContent(content);
        comment.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return commentRepository.saveComment(comment);
    }

    //----------------
    public PostEntity getPostById(String id) {
        PostEntity post = postRepository.findById(id);
        if (post.getPrivacy().equals("PUBLIC"))
            return post;
        if (post.getPrivacy().equals("PRIVATE"))
            throw new NotFoundException();
        if (ContextUserManager.getUserId().equals("anonymousUser"))
            return null;
        //--------------------

        RelationshipEntity relationship = relationshipService.getRelationship(ContextUserManager.getUserId(), post.getOwner_id());
        RelationshipStatus status = RelationshipStatus.valueOf(relationship.getStatus());
        return switch (status) {
            case FRIEND, CLOSE_FRIEND -> post;
            case BLOCK, PENDING -> throw new NotFoundException();
        };
    }

    public List<PostEntity> filterVisiblePost(List<PostEntity> listPost) {
        String current_user = ContextUserManager.getUserId();
        if (current_user.equals("anonymousUser"))
           return listPost.stream().filter(post -> post.getPrivacy().equals("PUBLIC")).collect(Collectors.toList());
        return listPost.stream().filter(post -> {
                    if (current_user.equals(post.getOwner_id()))  // Allow for post owner, right? :D
                        return true;
                    if (post.getPrivacy().equals("PRIVATE"))    // Private post is not allow for anyone (Case of owner just handle above)
                        return false;
                    RelationshipEntity relationship = relationshipService.getRelationship(current_user, post.getOwner_id());
                    RelationshipStatus status = RelationshipStatus.valueOf(relationship.getStatus());
                    switch (status) {
                        case FRIEND, CLOSE_FRIEND -> {
                            return true;
                        }
                        case BLOCK, PENDING -> {
                            return false;
                        }
                    }
                    return false;
                }
        ).collect(Collectors.toList());
    }
}
