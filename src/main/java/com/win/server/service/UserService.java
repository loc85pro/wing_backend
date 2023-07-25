package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUserById(String id) {
        return new UserDTO(userRepository.findById(id));
    }

    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username));
    }

}
