package com.ejder.socialapp.business.request.post;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private int id;
    private String title;
    private String text;
}
