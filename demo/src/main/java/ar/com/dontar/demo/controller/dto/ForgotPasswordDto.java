package ar.com.dontar.demo.controller.dto;

import jakarta.validation.constraints.Email;

public class ForgotPasswordDto {

    @Email
    private String username;

    public ForgotPasswordDto() {
    }

    public @Email String getUsername() {
        return username;
    }

    public void setUsername(@Email String username) {
        this.username = username;
    }
}
