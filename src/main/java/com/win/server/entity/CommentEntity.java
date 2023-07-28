package com.win.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="comment")
@Data
public class CommentEntity {
    @Id
    private String id;
    private String post_id;
    private String user_id;
    private String content;
    private Timestamp create_at;
    private Timestamp update_at;
}
