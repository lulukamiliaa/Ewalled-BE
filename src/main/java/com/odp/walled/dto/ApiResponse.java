package com.odp.walled.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private int statusCode;
    private T data;

    public ApiResponse(String status, int statusCode, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }

}
