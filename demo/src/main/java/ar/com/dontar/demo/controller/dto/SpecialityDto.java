package ar.com.dontar.demo.controller.dto;

import ar.com.dontar.demo.validation.annotation.ValidString;

public class SpecialityDto {

    @ValidString
    private String nameSpeciality;

    public String getNameSpeciality() {
        return nameSpeciality;
    }
    public void setNameSpeciality(String nameSpeciality) {
        this.nameSpeciality = nameSpeciality;
    }
}
