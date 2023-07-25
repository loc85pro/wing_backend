package com.win.server.DTO.auth.oauth;

import lombok.*;

@Data
public class OAuthUserInfo {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
