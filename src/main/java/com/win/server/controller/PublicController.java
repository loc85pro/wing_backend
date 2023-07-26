package com.win.server.controller;

import com.win.server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    private final FileService fileService;

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getAvatar(@RequestParam String user_id) throws FileNotFoundException, IOException {
        return fileService.loadAvatar(user_id);
    }
}
