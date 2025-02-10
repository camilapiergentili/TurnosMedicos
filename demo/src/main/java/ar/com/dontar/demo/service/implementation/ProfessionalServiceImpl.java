package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.mapper.ProfessionalMapper;
import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.model.Specialty;
import ar.com.dontar.demo.model.exception.ProfessionalAlreadyExistsException;
import ar.com.dontar.demo.model.exception.SpecialityNoExistsException;
import ar.com.dontar.demo.persistence.ProfessionalRepository;
import ar.com.dontar.demo.persistence.ScheduleRepository;
import ar.com.dontar.demo.persistence.SpecialityRepository;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import ar.com.dontar.demo.persistence.entity.ScheduleEntity;
import ar.com.dontar.demo.persistence.entity.SpecialityEntity;
import ar.com.dontar.demo.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    SpecialityRepository specialityRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ProfessionalMapper professionalMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerProffesional(ProfessionalDto professionalDto) throws ProfessionalAlreadyExistsException, SpecialityNoExistsException {

        Professional professional = professionalMapper.professionalDtoToModel(professionalDto);
        professional.setPassword(passwordEncoder.encode(professional.getPassword()));

        if(professionalRepository.findByDni(professional.getDni()) != null){
            throw new ProfessionalAlreadyExistsException("El profesional " + professional.getFirstName() + " " + professional.getLastName() + "ya esta registrado en la base de datos");
        }

        List<SpecialityEntity> specialityEntities = specialityRepository.findAllById(professionalDto.getIdSpecialityDtoList());

        if(specialityEntities.isEmpty()){
            throw new SpecialityNoExistsException("La especialidad no se encuentra registrada");
        }

        ProfessionalEntity professionalEntity = professionalMapper.professionalModelToEntity(professional, specialityEntities);

        professionalRepository.save(professionalEntity);

    }


}
