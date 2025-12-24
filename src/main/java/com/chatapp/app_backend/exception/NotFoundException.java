package com.chatapp.app_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException{
    public NotFoundException(
            int status,
            String error,
            String errorCode,
            String message){
        super(status,error,errorCode,message);
    }
}
