package com.odp.walled.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private int responseCode;
    private String message;
    private T data;

    public ApiResponse(int responseCode, String message, T data) {
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }


    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> success(int responseCode, String message, T data) {
        return new ApiResponse<>(responseCode, message, data);
    }

    public static <T> ApiResponse<T> error(int responseCode, String message, T data) {
        return new ApiResponse<>(responseCode, message, data);
    }

    public static <T> ApiResponse<T> error(int responseCode, String message) {
        return new ApiResponse<>(responseCode, message, null);
    }
}
