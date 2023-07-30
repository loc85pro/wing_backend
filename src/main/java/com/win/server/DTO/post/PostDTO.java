package com.win.server.DTO.post;

import com.win.server.DTO.UserDTO;
import com.win.server.entity.PostEntity;
import com.win.server.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String initiator_id;
    private String initiator_full_name;
    private String initiator_avatar;
    private String owner_id;
    private String owner_full_name;
    private String owner_avatar;
    private String privacy;
    private String caption;
    private String image;
    private Timestamp create_at;
    private Timestamp update_at;

    public PostDTO(PostEntity post, UserDTO initiator, UserDTO owner) {
        id=post.getId();
        privacy=post.getPrivacy();
        caption=post.getCaption();
        image=post.getImage();
        create_at=post.getCreate_at();
        update_at=post.getUpdate_at();

        initiator_id=initiator.getId();
        initiator_full_name=initiator.getFull_name();
        initiator_avatar=initiator.getAvatarURL();

        owner_id=owner.getId();
        owner_full_name=owner.getFull_name();
        owner_avatar=owner.getAvatarURL();
    }
}
