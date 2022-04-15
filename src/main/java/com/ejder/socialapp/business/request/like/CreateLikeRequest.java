package com.ejder.socialapp.business.request.like;

import lombok.Data;

@Data
public class CreateLikeRequest {
    private int userId;
    private int postId;

}
