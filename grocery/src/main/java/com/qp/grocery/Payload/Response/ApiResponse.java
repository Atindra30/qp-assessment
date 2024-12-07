package com.qp.grocery.Payload.Response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;

    private T data;
    public ApiResponse() {

    }
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}