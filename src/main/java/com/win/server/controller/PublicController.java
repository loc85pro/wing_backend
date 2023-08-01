package com.win.server.controller;

import com.win.server.DTO.UserDTO;
import com.win.server.constant.FileSupporter;
import com.win.server.exception.myexception.FileGeneralException;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.service.FileService;
import com.win.server.service.MailService;
import com.win.server.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name = "Public API")
public class PublicController {
    private final FileService fileService;
    private final UserService userService;
    private final MailService mailService;

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getAvatar(@RequestParam String user_id) throws  IOException {
        return fileService.loadAvatar(user_id);
    }

    @GetMapping(value = "/post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getPostMediaById(@RequestParam("file_name") String image_name, HttpServletResponse rs) {
        try {
            String type = "image/png";
            if (image_name.substring(image_name.lastIndexOf(".")).equals(".mp4"))
                type="video/mp4";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type",type);

            return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile(image_name));
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

    @GetMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getPublicFile(@RequestParam String file_name) {
        String extensionName = FileSupporter.getExtensionName(file_name);
        String contentType = FileSupporter.getContentTypeByExtension(extensionName);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Content-type" + contentType);
        headers.add("Content-type", contentType);
        return ResponseEntity.status(200).headers(headers).body(fileService.loadPublicFile(file_name));
    }

    @GetMapping("/mail")
    public String sendMail(@RequestParam String content) {
        mailService.sendEmail("" ,content);
        return content + " sent";
    }
}
