package com.win.server.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentUploadRequest {
    private String post_id;
    private String content;
}
