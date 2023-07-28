package com.win.server.service;

import com.win.server.entity.PostEntity;
import com.win.server.repository.PostRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
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

    public PostEntity getPostById(String post_id) {
        return postRepository.findById(post_id);
    }

    public List<PostEntity> getPostByOwner(String owner_id) {
        return postRepository.findByOwner(owner_id);
    }
}
