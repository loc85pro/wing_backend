package com.win.server.DTO.relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipElementDTO {
    private String id;
    private String user_id;
    private String user_name;
    private String user_full_name;
    private String avatar;
    private String status;
    private Timestamp create_at;
}
