package com.win.server.controller;

import com.win.server.constant.PostPrivacy;
import com.win.server.entity.PostEntity;
import com.win.server.security.ContextUserManager;
import com.win.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
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
    public PostEntity getPostById(@RequestParam("id") String id) {
        return null;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public List<PostEntity> getOwnPost() {
        String current_user_id = ContextUserManager.getUserId();
        return postService.getPostByOwner(current_user_id);
    }
}
