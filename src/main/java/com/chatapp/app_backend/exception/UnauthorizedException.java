package com.chatapp.app_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends  BaseException{

    

    public UnauthorizedException(
            int status,
            String error,
            String errorCode,
            String message
    ){
        super(status,error,errorCode,message);

    }

}
