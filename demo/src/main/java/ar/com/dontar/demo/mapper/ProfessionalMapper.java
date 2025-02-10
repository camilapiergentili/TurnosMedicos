package ar.com.dontar.demo.mapper;

import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.model.UserType;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import ar.com.dontar.demo.persistence.entity.SpecialityEntity;


import java.util.ArrayList;
import java.util.List;

public class ProfessionalMapper {

    public Professional professionalDtoToModel(ProfessionalDto professionalDto){
        Professional professional = new Professional();
        professional.setDni(professionalDto.getDni());
        professional.setFirstName(professionalDto.getFirstName());
        professional.setLastName(professionalDto.getLastName());
        professional.setEmail(professionalDto.getEmail());
        professional.setPassword(professionalDto.getPassword());
        professional.setUserType(UserType.PROFESIONAL);
        professional.setMatricula(professionalDto.getMatricula());

        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleDto scheduleDto : professionalDto.getScheduleDtoList()){
            schedules.add(new Schedule(scheduleDto, professional));
        }

        professional.setSchedules(schedules);


        return professional;
    }

    public ProfessionalEntity professionalModelToEntity(Professional professional, List<SpecialityEntity> specialities){

        return new ProfessionalEntity(professional, specialities);
    }
}
