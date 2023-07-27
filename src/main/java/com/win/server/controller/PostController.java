package com.win.server.controller;

import com.win.server.DTO.post.CreatePostRequest;
import com.win.server.entity.PostEntity;
import com.win.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostEntity createPost(@RequestParam String caption, @RequestParam("file") MultipartFile file) {
        return postService.createPost(caption ,file);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostEntity getPostById(@RequestParam("id") String id) {
        return null;
    }

}
