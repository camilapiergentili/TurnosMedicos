package ar.com.dontar.demo.controller.dto;


import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfessionalDto extends UserDto {

    private Set<Long> idSpecialityDtoList = new HashSet<>();

    @Pattern(regexp = "^[0-9]{4,}$", message = "La matricula debe contar con caracteres números")
    private String matricula;

    public Set<Long> getIdSpecialityDtoList() {
        return idSpecialityDtoList;
    }

    public void setIdSpecialityDtoList(Set<Long> idSpecialityDtoList) {
        this.idSpecialityDtoList = idSpecialityDtoList;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
