package com.example.spring_security.util;

public class EntityUserErrorResponse {
    private String message;

    public EntityUserErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
