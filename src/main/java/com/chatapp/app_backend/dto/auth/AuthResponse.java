package com.chatapp.app_backend.dto.auth;

public class AuthResponse {
    private String userId;
    private String email;
    private String token ;

    public AuthResponse(){

    }

    public AuthResponse(String userId , String email,  String token){
        this.userId = userId;
        this.email = email;
        this.token = token;
    }

    public  String getEmail(){
        return email;
    }

    public  String getUserId(){
        return userId;
    }

    public String getToken (){
        return token;
    }

}
