package ar.com.dontar.demo.controller.dto;

import ar.com.dontar.demo.validation.PasswordConfirmation;
import ar.com.dontar.demo.validation.annotation.PasswordMatches;
import ar.com.dontar.demo.validation.annotation.ValidPassword;
import jakarta.validation.constraints.NotNull;

@PasswordMatches
public class PasswordChangeDto implements PasswordConfirmation {

    private String oldPassword;

    @NotNull(message = "La contraseña no puede estar vacia")
    @ValidPassword
    private String password;

    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
