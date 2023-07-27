package com.win.server.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private String caption;
}
