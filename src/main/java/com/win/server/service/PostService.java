package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.DTO.post.CommentDTO;
import com.win.server.DTO.post.PostDTO;
import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.CommentEntity;
import com.win.server.entity.PostEntity;
import com.win.server.entity.RelationshipEntity;
import com.win.server.exception.myexception.ForbiddenException;
import com.win.server.exception.myexception.NotFoundException;
import com.win.server.repository.CommentRepository;
import com.win.server.repository.PostRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;
    private final RelationshipService relationshipService;
    private final UserService userService;
    public PostDTO createPost(String caption, MultipartFile media, String privacy) {
        PostEntity newPost = new PostEntity();
        newPost.setId(UUID.randomUUID().toString());
        newPost.setCreate_at(new Timestamp(System.currentTimeMillis()));
        String user_current = ContextUserManager.getUserId();
        newPost.setOwner_id(user_current);
        newPost.setInitiator_id(user_current);
        newPost.setCaption(caption);
        newPost.setPrivacy(privacy);
        //----- SAVE MEDIA FILE ------
//        String fileId =  UUID.randomUUID().toString().replace("-","");
//        String fileName = fileId + ".png";
//        fileService.saveFile(media, fileName, "src/main/resources/public/image");
//        String getImageURL = "/public/post?id=" + fileId;
        String file_name = fileService.savePublicFile(media);
        newPost.setImage("/public/file?file_name="+file_name);
        postRepository.savePost(newPost);
        return convertToDTO(postRepository.findById(newPost.getId()));
    }


    public List<PostDTO> getPostByUserId(String user_id) {
        List<PostEntity> listPost = postRepository.findByOwner(user_id);
        return  filterVisiblePost(listPost).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PostDTO convertToDTO(PostEntity post) {
        UserDTO initiator = userService.getUserById(post.getInitiator_id());
        UserDTO owner = userService.getUserById(post.getOwner_id());
        return new PostDTO(post, initiator, owner);
    }

    public List<PostDTO> getPostByUsername(String username) {
        UserDTO user = userService.getUserByUsername(username);
        return getPostByUserId(user.getId());
    }


    //----------------
    public PostDTO getPostById(String id) {
        PostEntity post = postRepository.findById(id);
        if (post.getPrivacy().equals("PUBLIC"))
            return convertToDTO(post);
        if (post.getPrivacy().equals("PRIVATE"))
            throw new NotFoundException();
        if (ContextUserManager.getUserId().equals("anonymousUser"))
            return null;
        //--------------------

        RelationshipEntity relationship = relationshipService.getRelationship(ContextUserManager.getUserId(), post.getOwner_id());
        RelationshipStatus status = RelationshipStatus.valueOf(relationship.getStatus());
        return switch (status) {
            case FRIEND, CLOSE_FRIEND -> convertToDTO(post);
            case BLOCK, PENDING -> throw new NotFoundException();
        };
    }

    public List<PostEntity> filterVisiblePost(List<PostEntity> listPost) {
        String current_user = ContextUserManager.getUserId();
        if (current_user.equals("anonymousUser"))
           return listPost.stream().filter(post -> post.getPrivacy().equals("PUBLIC")).collect(Collectors.toList());
        return listPost.stream().filter(post -> {
                    if (post.getPrivacy().equals("PUBLIC"))
                        return true;
                    if (current_user.equals(post.getOwner_id()))  // Allow for post owner, right? :D
                        return true;
                    if (post.getPrivacy().equals("PRIVATE"))    // Private post is not allow for anyone (Case of owner just handle above)
                        return false;
                    RelationshipEntity relationship = relationshipService.getRelationship(current_user, post.getOwner_id());
                    if (relationship==null)
                        return post.getPrivacy().equals("PUBLIC");

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
    public List<CommentDTO> getCommentByPostId(String post_id) {
        List<CommentEntity> listComment = commentRepository.getAllCommentByPost(post_id);
        List<CommentDTO>  rs = new ArrayList<CommentDTO>();
        listComment.forEach(comment -> {
            CommentDTO temp = new CommentDTO();
            temp.setId(comment.getId());
            temp.setUser_id(comment.getUser_id());
            temp.setPost_id(comment.getPost_id());
            temp.setContent(comment.getContent());
            temp.setUpdate_at(comment.getUpdate_at());
            temp.setCreate_at(comment.getCreate_at());
            UserDTO user = userService.getUserById(temp.getUser_id());
            temp.setFull_name(user.getFull_name());
            temp.setAvatar(user.getAvatarURL());
            rs.add(temp);
        });
        return rs;
    }

    public List<PostEntity>  getAllVisibleOfFriendPost() {
        String current_user = ContextUserManager.getUserId();
        List<RelationshipEntity> relationshipList = relationshipService.getRelationship(current_user);
        List<String> friendIdList = new ArrayList<String>();
        relationshipList.forEach(relationship -> {
            friendIdList.add(relationship.getUser_1().equals(current_user) ? relationship.getUser_2(): relationship.getUser_1());
        });
        List<PostEntity> listPost = new ArrayList<PostEntity>();
        friendIdList.forEach(id-> {
            listPost.addAll(postRepository.findByOwner(id));
        });
        return  listPost.stream().filter(post -> !post.getPrivacy().equals("PRIVATE")).sorted().toList();
    }

    public List<PostDTO> getNewFeed() {
        List<PostDTO> friendPost = getAllVisibleOfFriendPost().stream().map(this::convertToDTO).collect(Collectors.toList());
        List<PostDTO> mine = getPostByUserId(ContextUserManager.getUserId());
        friendPost.addAll(mine);
        return friendPost;
    }

    public void deletePostById(String post_id) {
        PostEntity post = postRepository.findById(post_id);
        String current_user = ContextUserManager.getUserId();
        if (post.getOwner_id().equals(current_user))
            postRepository.remove(post);
        else
            throw new ForbiddenException();
    }
    public void editPostById(String post_id, String caption, MultipartFile media, String privacy) {
        PostEntity post = postRepository.findById(post_id);
        String current_user = ContextUserManager.getUserId();
        if (post.getOwner_id().equals(current_user)) {
            if (caption!=null)
                post.setCaption(caption);
            if (media!=null)
            {
                String fileName = fileService.savePublicFile(media);
                post.setImage("/public/file?file_name="+fileName);
            }
            if (privacy!=null)
                post.setPrivacy(privacy);
            post.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            postRepository.update(post);
        }
    }
    //---------------------------COMMENT--------------------
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

    public CommentEntity editComment(String comment_id, String content) {
        String current_user = ContextUserManager.getUserId();
        CommentEntity comment = commentRepository.getCommentById(comment_id);
        if (!current_user.equals(comment.getUser_id()))
            throw new ForbiddenException();
        comment.setContent(content);
        comment.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        commentRepository.update(comment);
        return comment;
    }

    public void deleteComment(String comment_id) {
        String current_user = ContextUserManager.getUserId();
        CommentEntity comment = commentRepository.getCommentById(comment_id);
        PostDTO post = getPostById(comment.getPost_id());
        if (!(current_user.equals(comment.getUser_id())||current_user.equals(post.getOwner_id())))
            throw new ForbiddenException();
        commentRepository.remove(comment);
    }

}
