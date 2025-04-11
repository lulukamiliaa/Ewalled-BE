package com.odp.walled.dto.common;

import lombok.Getter;
import lombok.Setter;

/**
 * A generic API response wrapper used to standardize API responses across the application.
 *
 * @param <T> the type of the data payload
 */
@Getter
@Setter
public class ApiResponse<T> {

    /**
     * HTTP-like status code representing the result of the API call.
     */
    private int responseCode;

    /**
     * Message providing details about the response (e.g., success or error information).
     */
    private String message;

    /**
     * The actual data returned in the response body. Can be {@code null}.
     */
    private T data;

    /**
     * Constructs a new {@code ApiResponse} with the given response code, message, and data.
     *
     * @param responseCode the response status code
     * @param message      the response message
     * @param data         the response data payload
     */
    public ApiResponse(int responseCode, String message, T data) {
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }

    /**
     * Creates a successful API response with default 200 status code.
     *
     * @param message the success message
     * @param data    the data payload
     * @param <T>     the type of the data
     * @return a successful {@code ApiResponse}
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * Creates a successful API response with custom status code.
     *
     * @param responseCode the custom success code
     * @param message      the success message
     * @param data         the data payload
     * @param <T>          the type of the data
     * @return a successful {@code ApiResponse}
     */
    public static <T> ApiResponse<T> success(int responseCode, String message, T data) {
        return new ApiResponse<>(responseCode, message, data);
    }

    /**
     * Creates an error response with data.
     *
     * @param responseCode the error status code
     * @param message      the error message
     * @param data         the data payload (e.g., error details or field violations)
     * @param <T>          the type of the data
     * @return an error {@code ApiResponse}
     */
    public static <T> ApiResponse<T> error(int responseCode, String message, T data) {
        return new ApiResponse<>(responseCode, message, data);
    }

    /**
     * Creates an error response without data.
     *
     * @param responseCode the error status code
     * @param message      the error message
     * @param <T>          the type of the data (typically {@code Void})
     * @return an error {@code ApiResponse} with {@code null} data
     */
    public static <T> ApiResponse<T> error(int responseCode, String message) {
        return new ApiResponse<>(responseCode, message, null);
    }
}
