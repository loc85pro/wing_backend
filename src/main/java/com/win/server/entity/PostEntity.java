package com.win.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post")
public class PostEntity implements Comparable<PostEntity>{
    @Id
    private String id;
    private String initiator_id;
    private String owner_id;
    private String privacy;
    private String caption;
    private String image;
    private Timestamp create_at;
    private Timestamp update_at;

    @Override
    public int compareTo(PostEntity o) {
        Timestamp thisPost = o.getUpdate_at()==null ? o.getCreate_at() : o.getUpdate_at();
        Timestamp comparedPost = this.getUpdate_at()==null ? this.getCreate_at() : this.getUpdate_at();
        return thisPost.compareTo(comparedPost);
    }
}