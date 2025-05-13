package ar.com.dontar.demo.controller.dto;


import ar.com.dontar.demo.validation.annotation.ValidString;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class PatientDto extends UserDto {

    private String dateOfBirth;

    @ValidString
    private String obraSocial;

    @Pattern(regexp = "^[0-9]{8,}$", message = "El número de telefo debe contar con caracteres números")
    private String phone;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
