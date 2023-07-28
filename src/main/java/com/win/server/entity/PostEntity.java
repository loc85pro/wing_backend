package com.win.server.entity;

import com.win.server.constant.PostPrivacy;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post")
public class PostEntity {
    @Id
    private String id;
    private String initiator_id;
    private String owner_id;
    private String privacy;
    private String caption;
    private String image;
    private Timestamp create_at;
    private Timestamp update_at;
}