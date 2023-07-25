package com.win.server.DTO.auth.oauth;

import lombok.*;

import java.io.Serializable;

@Data
public class OAuthTokenResponse implements Serializable {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String scope;
    private String id_token;
}
