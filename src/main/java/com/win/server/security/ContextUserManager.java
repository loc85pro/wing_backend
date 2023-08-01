package com.win.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ContextUserManager {
    static public String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else
            return principal.toString();
    }
    static public Map<String, String> edit_email_code = new HashMap<String, String>();
    static public Map<String, String> new_password_required = new HashMap<String, String>();
}
