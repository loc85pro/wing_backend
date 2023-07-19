package com.win.server.security;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {
    public String getAuthority() {
        return "ADMIN";
    }
}
