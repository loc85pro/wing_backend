package com.win.server.controller;

import com.win.server.exception.myexception.FileGeneralException;
import com.win.server.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name = "Public API")
public class PublicController {
    private final FileService fileService;

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getAvatar(@RequestParam String user_id) throws  IOException {
        return fileService.loadAvatar(user_id);
    }

    @GetMapping(value = "/post", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPostMediaById(@RequestParam("id") String image_id) {
        try {
            return fileService.loadPublicImage(image_id);
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
}
