package com.win.server.DTO.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SimpleMessage {
    private String message;
    private int code;
    private String detail;
}
