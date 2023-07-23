package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserDTO getUserById(String id) {
        return new UserDTO(userRepository.getReferenceById(id));
    }

    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUser_name(username));
    }
}
