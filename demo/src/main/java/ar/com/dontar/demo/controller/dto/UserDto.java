package ar.com.dontar.demo.controller.dto;

import ar.com.dontar.demo.validation.annotation.PasswordMatches;
import ar.com.dontar.demo.validation.annotation.ValidDniLong;
import ar.com.dontar.demo.validation.annotation.ValidPassword;
import ar.com.dontar.demo.validation.annotation.ValidString;
import jakarta.validation.constraints.*;

@PasswordMatches
public class UserDto {

    @ValidDniLong
    private long dni;

    @ValidString
    private String firstName;

    @ValidString
    private String lastName;

    @NotBlank(message = "El campo no puede estar vacio")
    @Email(message = "Debe tener formato de email")
    private String username;

    @NotNull(message = "La contraseña no puede estar vacia")
    @ValidPassword
    private String password;

    private String confirmPassword;

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
