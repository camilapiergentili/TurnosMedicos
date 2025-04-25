package ar.com.dontar.demo.model;

import ar.com.dontar.demo.controller.dto.SpecialityDto;

import java.util.ArrayList;
import java.util.List;

public class Speciality {

    private long idSpeciality;
    private String specialityName;

    private List<Professional> professionals;

    public Speciality(){
        this.professionals = new ArrayList<>();
    }

    public long getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(long idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<Professional> professionals) {
        this.professionals = professionals;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
