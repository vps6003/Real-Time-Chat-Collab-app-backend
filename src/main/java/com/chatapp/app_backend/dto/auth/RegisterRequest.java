package com.chatapp.app_backend.dto.auth;

public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public RegisterRequest() {}

    public String getEmail() { return email;}

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
