package ar.com.dontar.demo.controller.dto;

import jakarta.validation.constraints.Email;

public class LoginRequest {

    @Email
    private String username;
    private String password;

    public LoginRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
