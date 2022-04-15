package com.ejder.socialapp.business.request.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String text;
    private int userId;
}
