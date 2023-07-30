package com.win.server.controller;

import com.win.server.DTO.UserDTO;
import com.win.server.exception.myexception.FileGeneralException;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.security.ContextUserManager;
import com.win.server.service.FileService;
import com.win.server.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name = "Public API")
public class PublicController {
    private final FileService fileService;
    private final UserService userService;

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getAvatar(@RequestParam String user_id) throws  IOException {
        return fileService.loadAvatar(user_id);
    }

    @GetMapping(value = "/post", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPostMediaById(@RequestParam("file_name") String image_name) {
        try {
            return fileService.loadPublicImage(image_name);
        }   catch (Exception ex) {
            throw new FileGeneralException();
        }
    }

    @GetMapping(value = "/general", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getGeneralFile(@RequestParam String file_name) {
        try {
            return fileService.loadPublicGeneralFile(file_name);
        }   catch (Exception ex) {
            throw new FileGeneralException();
        }
    }
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByIdOrUsername(@RequestParam(required = false) String id , @RequestParam(required = false) String username) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (id==null && username==null)
            throw new UserNotFoundException();
        if (id!=null)
            return userService.getUserById(id);
        else
            return userService.getUserByUsername(username);
    }
}
