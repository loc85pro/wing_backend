package com.win.server.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="comment")
@Data
public class CommentEntity {
    @Id
    private String id;
    private String post_id;
    private String user_id;
    private String content;
}
