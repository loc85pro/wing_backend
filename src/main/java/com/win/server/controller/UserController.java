package com.win.server.controller;

import com.win.server.DTO.UserDTO;
import com.win.server.DTO.auth.SimpleMessage;
import com.win.server.security.ContextUserManager;
import com.win.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "Personal Data")
public class UserController {
    private final UserService userService;

    @Operation(description = "Get own data (Brief data)")
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getOwnData() {
        return userService.getUserById(ContextUserManager.getUserId());
    }
    @PostMapping("/avatar")
    public SimpleMessage uploadAvatar(@RequestParam("file") MultipartFile file) {
        userService.uploadAvatar(file);
        return new SimpleMessage("Successfully",200,"Upload done!");
    }

}
