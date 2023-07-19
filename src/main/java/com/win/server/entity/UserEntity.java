package com.win.server.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "user")
public class UserEntity {
    @Id
    private String id;

    private String username;

    private String password;

    private String full_name;

    private String email;

    private String phone;
}
