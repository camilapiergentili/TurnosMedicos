package ar.com.dontar.demo.controller.response;


import java.util.List;

public class ProfessionalResponse extends UserResponse {

    private String matricula;
    private List<SpecialityResponse> specialities;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<SpecialityResponse> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<SpecialityResponse> specialities) {
        this.specialities = specialities;
    }
}
