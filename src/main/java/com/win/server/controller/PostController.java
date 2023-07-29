package com.win.server.controller;

import com.win.server.DTO.post.CommentDTO;
import com.win.server.DTO.post.CommentUploadRequest;
import com.win.server.constant.PostPrivacy;
import com.win.server.entity.CommentEntity;
import com.win.server.entity.PostEntity;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.security.ContextUserManager;
import com.win.server.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "API for post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostEntity createPost(@RequestParam String caption, @RequestParam PostPrivacy privacy, @RequestParam("file") MultipartFile file) {
        String privacyPost = privacy.toString();
        return postService.createPost(caption ,file, privacyPost);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostEntity getPostById(@RequestParam String id) {
        return postService.getPostById(id);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public List<PostEntity> getOwnPost() {
        String current_user_id = ContextUserManager.getUserId();
        return postService.getPostByUserId(current_user_id);
    }

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentEntity commentIntoPost(@RequestBody CommentUploadRequest request) {
        return postService.uploadComment(request.getPost_id(), request.getContent());
    }

    @GetMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getPostComment(@RequestParam String post_id) {
        return postService.getCommentByPostId(post_id);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<PostEntity> getPostByUser(@RequestParam(required = false) String user_id, @RequestParam(required = false) String username) {
        if (user_id==null && username==null)
            throw new UserNotFoundException();
        if (user_id!=null)
            return postService.getPostByUserId(user_id);
        else
            return postService.getPostByUsername(username);
    }
    //-------------
    @GetMapping("/new_feed")
    @ResponseStatus(HttpStatus.OK)
    public List<PostEntity> getNewFeed() {
        return postService.getAllVisibleOfFriendPost();
    }

}
