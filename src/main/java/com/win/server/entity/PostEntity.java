package com.win.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name="post")
public class PostEntity {
    @Id
    private String id;
    private String initiator_id;
    private String owner_id;
    private String caption;
    private String image;
    private Timestamp create_at;
    private Timestamp update_at;
}