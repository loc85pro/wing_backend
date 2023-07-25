package com.win.server.DTO.auth.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PhonePasswordLoginRequest {
    private String phone;
    private String password;
}
