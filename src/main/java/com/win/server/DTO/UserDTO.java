package com.win.server.DTO;

import com.win.server.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDTO {
    private String id;
    private String user_name;
    private String full_name;
    private String avatarURL;

    public UserDTO(UserEntity entity) {
        this.id=entity.getId();
        this.user_name=entity.getUsername();
        this.full_name=entity.getFull_name();
        this.avatarURL = "/public/avatar?user_id=" + entity.getId();
    }
}
