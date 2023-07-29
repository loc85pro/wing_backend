package com.win.server.DTO.post;

import com.win.server.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO  {
    private String id;
    private String post_id;
    private String user_id;
    private String content;
    private Timestamp create_at;
    private Timestamp update_at;

    private String full_name;
    private String avatar;
}
