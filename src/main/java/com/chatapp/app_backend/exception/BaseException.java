package com.chatapp.app_backend.exception;

public abstract class BaseException extends RuntimeException {

    private final int status;
    private final String error;
    private final String errorCode;

    protected BaseException(
            int status,
            String error,
            String errorCode,
            String message
    ) {
        super(message);
        this.status = status;
        this.error = error;
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
