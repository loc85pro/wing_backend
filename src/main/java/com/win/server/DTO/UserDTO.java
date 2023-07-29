package com.win.server.DTO;

import com.win.server.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String user_name;
    private String full_name;
    private String avatarURL;

    public UserDTO(UserEntity entity) {
        this.id=entity.getId();
        this.user_name=entity.getUser_name();
        this.full_name=entity.getFull_name();
        this.avatarURL = "/public/avatar?user_id=" + entity.getId();
    }
}
