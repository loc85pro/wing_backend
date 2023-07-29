package com.win.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "relationship")
public class RelationshipEntity {
    @Id
    private String id;
    private String user_1;
    private String user_2;
    private String status;
    private Timestamp create_at;
}
