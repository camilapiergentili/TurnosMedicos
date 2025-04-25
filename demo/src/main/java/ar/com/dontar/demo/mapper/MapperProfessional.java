package ar.com.dontar.demo.mapper;

//import ar.com.dontar.demo.controller.ProfessionalController;
import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.response.ProfessionalResponse;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.model.*;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperProfessional {

    public static Professional professionalDtoToModel(ProfessionalDto professionalDto){
        Professional professional = new Professional();
        professional.setDni(professionalDto.getDni());
        professional.setFirstName(professionalDto.getFirstName());
        professional.setLastName(professionalDto.getLastName());
        professional.setUsername(professionalDto.getUsername());
        professional.setPassword(professionalDto.getPassword());
        professional.setUserType(UserType.PROFESIONAL);
        professional.setMatricula(professionalDto.getMatricula());


        return professional;
    }

    public static ProfessionalEntity professionalModelToEntity(Professional professional){

        ProfessionalEntity entity = new ProfessionalEntity();
        entity.setDni(professional.getDni());
        entity.setFirstName(professional.getFirstName());
        entity.setLastName(professional.getLastName());
        entity.setUsername(professional.getUsername());
        entity.setPassword(professional.getPassword());
        entity.setUserType(professional.getUserType());
        entity.setMatricula(professional.getMatricula());

        return entity;
    }


    public static Professional professionalEntityToModel(ProfessionalEntity professionalEntity){
        Professional professional = new Professional();
        professional.setIdUser(professionalEntity.getIdUser());
        professional.setDni(professionalEntity.getDni());
        professional.setFirstName(professionalEntity.getFirstName());
        professional.setLastName(professionalEntity.getLastName());
        professional.setUsername(professionalEntity.getUsername());
        professional.setPassword(professionalEntity.getPassword());
        professional.setMatricula(professionalEntity.getMatricula());
        professional.setUserType(professionalEntity.getUserType());

        if (professionalEntity.getSpecialities() != null && !professionalEntity.getSpecialities().isEmpty()) {
            Set<Speciality> specialityModel = professionalEntity.getSpecialities().stream()
                    .map(MapperSpeciality::specialityEntityToModel)
                    .collect(Collectors.toSet());
            professional.setSpecialties(specialityModel);
        }

        if(professionalEntity.getScheduleEntity() != null && !professionalEntity.getScheduleEntity().isEmpty()){
            List<Schedule> schedulesModel = professionalEntity.getScheduleEntity().stream()
                    .map(ScheduleMapper::scheduleEntityToModel)
                    .collect(Collectors.toList());
            professional.setSchedules(schedulesModel);
        }

        if(professionalEntity.getAppointmentsProfessional() != null && !professionalEntity.getAppointmentsProfessional().isEmpty()){
            List<Appointment> appointmentsModel = professionalEntity.getAppointmentsProfessional().stream()
                    .map(MapperAppointment::appointmentEntityToModel)
                    .collect(Collectors.toList());
            professional.setAppointmentsProfessional(appointmentsModel);
        }

        return professional;
    }

    public static ProfessionalResponse professionalEntityToResponse(ProfessionalEntity professionalEntity) {
        ProfessionalResponse professionalResponse = new ProfessionalResponse();
        professionalResponse.setId(professionalEntity.getIdUser());
        professionalResponse.setDni(professionalEntity.getDni());
        professionalResponse.setFirstName(professionalEntity.getFirstName());
        professionalResponse.setLastName(professionalEntity.getLastName());
        professionalResponse.setMatricula(professionalEntity.getMatricula());
        professionalResponse.setUsername(professionalEntity.getUsername());

        if(professionalEntity.getSpecialities() != null && !professionalEntity.getSpecialities().isEmpty()) {
            List<SpecialityResponse> specialities = professionalEntity.getSpecialities().stream()
                    .map(MapperSpeciality::specialityEntityToResponse)
                    .collect(Collectors.toList());
            professionalResponse.setSpecialities(specialities);
        }

        return professionalResponse;
    }
}
