package com.odp.walled.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int statusCode;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, 200, data);
    }

    public static <T> ApiResponse<T> success(String message, int statusCode, T data) {
        return new ApiResponse<>(true, message, statusCode, data);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode, T data) {
        return new ApiResponse<>(false, message, statusCode, data);
    }

    public static ApiResponse<Void> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, statusCode, null);
    }
}
