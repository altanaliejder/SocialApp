package com.ejder.socialapp.business.request.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String text;
    private int postId;
    private Long userId;
}
