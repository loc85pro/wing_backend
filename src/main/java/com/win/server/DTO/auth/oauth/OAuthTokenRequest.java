package com.win.server.DTO.auth.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class OAuthTokenRequest implements Serializable {
    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String redirect_url;
    private String access_type;
}
