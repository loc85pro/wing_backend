package com.win.server.DTO;

import com.win.server.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String user_name;
    private String full_name;
    private String email;
    private String phone;

    public UserDTO(UserEntity entity) {
        this.id=entity.getId();
        this.user_name=entity.getUsername();
        this.full_name=entity.getFull_name();
        this.email=entity.getEmail();
        this.phone=entity.getPhone();
    }
}
