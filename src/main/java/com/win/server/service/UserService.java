package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.entity.UserEntity;
import com.win.server.exception.myexception.IncorrectPasswordException;
import com.win.server.repository.UserRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserDTO getUserById(String id) {
        return new UserDTO(userRepository.findById(id));
    }

    public UserDTO getUserByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username));
    }


    //-------- EDIT AREA ----------------
    public void uploadAvatar(MultipartFile file)  {
        String userId = ContextUserManager.getUserId();
        String avatarPath = "src/main/resources/public/user/" + userId;
        fileService.saveFile(file, "avatar.png", avatarPath);
    }

    public void editProfile(String username, String fullName, MultipartFile avatar, String phone) {
        UserEntity user = userRepository.findById(ContextUserManager.getUserId());
        if (username!=null)
            user.setUser_name(username);
        if (fullName!=null)
            user.setFull_name(fullName);
        if (phone!=null)
            user.setPhone(phone);
        if (avatar!=null)
            uploadAvatar(avatar);
        userRepository.update(user);
    }

    public UserDTO editEmail(String password, String email) {
        UserEntity user = userRepository.findById(ContextUserManager.getUserId());
        if (passwordEncoder.matches(password, user.getPassword()))
        {
            user.setEmail(email);
            userRepository.update(user);
            return new UserDTO(user);
        }
        else
            throw new IncorrectPasswordException();
    }
    public void sendCodeToChangePassword(String newPassword) {
        Random num = new Random(System.currentTimeMillis());
        String code = String.valueOf((100000+num.nextInt()%100000));
        UserEntity user = userRepository.findById(ContextUserManager.getUserId());
        mailService.sendEmail(user.getEmail(), code);
        ContextUserManager.edit_email_code.put(user.getId(), code);
        ContextUserManager.new_password_required.put(user.getId(), newPassword);
    }

    public UserDTO editPasswordByEmailCodeVerify(String code) {
        String current_user = ContextUserManager.getUserId();
        String correctCode = ContextUserManager.edit_email_code.get(current_user);
        if (code.equals(correctCode))
        {
            UserEntity user = userRepository.findById(current_user);
            String encodedPassword = passwordEncoder.encode(ContextUserManager.new_password_required.get(current_user));
            user.setPassword(encodedPassword);

            ContextUserManager.edit_email_code.remove(current_user);
            ContextUserManager.new_password_required.remove(current_user);

            userRepository.update(user);
            return new UserDTO(user);
        }
        throw new IncorrectPasswordException();
    }
}
