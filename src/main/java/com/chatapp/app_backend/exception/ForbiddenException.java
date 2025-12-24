package com.chatapp.app_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException{
    public ForbiddenException(
            int status,
            String error,
            String errorCode,
            String message){
        super(status, error, errorCode, message);
    }
}
