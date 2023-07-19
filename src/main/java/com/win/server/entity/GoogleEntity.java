package com.win.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleEntity {
    @Id
    private String id;

    private String given_name;

    private String family_name;

    private String picture;

    private String locale;

    private String access_token;

    private String refresh_token;

    private String id_token;

    private String scope;

    private String expires_in;
}
