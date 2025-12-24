package com.chatapp.app_backend.dto.auth;

public class LoginRequest {
    private  String email;
    private  String password;

    public LoginRequest() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
