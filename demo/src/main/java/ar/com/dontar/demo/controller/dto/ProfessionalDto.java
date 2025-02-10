package ar.com.dontar.demo.controller.dto;


import java.util.ArrayList;
import java.util.List;

public class ProfessionalDto extends UserDto {

    private List<Long> idSpecialityDtoList = new ArrayList<>();
    private List<ScheduleDto> scheduleDtoList = new ArrayList<>();
    private String matricula;

    public List<Long> getIdSpecialityDtoList() {
        return idSpecialityDtoList;
    }

    public void setIdSpecialityDtoList(List<Long> idSpecialityDtoList) {
        this.idSpecialityDtoList = idSpecialityDtoList;
    }

    public List<ScheduleDto> getScheduleDtoList() {
        return scheduleDtoList;
    }

    public void setScheduleDtoList(List<ScheduleDto> scheduleDtoList) {
        this.scheduleDtoList = scheduleDtoList;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
