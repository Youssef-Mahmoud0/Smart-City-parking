package com.databaseProject.backend.dto;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    // Getter and setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
