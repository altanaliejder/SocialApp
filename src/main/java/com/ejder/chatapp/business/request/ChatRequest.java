package com.ejder.chatapp.business.request;

import lombok.Data;

@Data
public class ChatRequest {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
}
