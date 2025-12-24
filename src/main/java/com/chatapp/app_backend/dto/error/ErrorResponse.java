package com.chatapp.app_backend.dto.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String errorCode;
    private final String path;

    public ErrorResponse(
            int status,
            String error,
            String errorCode,
            String message,
            String path
    ) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
