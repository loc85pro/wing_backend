package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.repository.UserRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    public UserDTO getUserById(String id) {
        return new UserDTO(userRepository.findById(id));
    }

    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username));
    }

    public void uploadAvatar(MultipartFile file)  {
        String userId = ContextUserManager.getUserId();
        String avatarPath = "src/main/resources/public/user/" + userId;
        fileService.saveFile(file, "avatar.png", avatarPath);
    }
}
