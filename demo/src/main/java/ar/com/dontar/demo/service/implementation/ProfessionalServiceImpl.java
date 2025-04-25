package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.response.ProfessionalResponse;
import ar.com.dontar.demo.mapper.MapperProfessional;
import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.exception.ProfessionalAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.persistence.ProfessionalRepository;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import ar.com.dontar.demo.persistence.entity.SpecialityEntity;
import ar.com.dontar.demo.service.ProfessionalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private SpecialityServiceImpl specialityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerProfessional(ProfessionalDto professionalDto) throws ProfessionalAlreadyExistsException, SpecialityNotExistsException {

        Professional professional = MapperProfessional.professionalDtoToModel(professionalDto);

        if(professionalRepository.findByDni(professional.getDni()).isPresent()){
            throw new ProfessionalAlreadyExistsException("El profesional " + professional.getFirstName() + " " + professional.getLastName() + " ya esta registrado");
        }

        if(professionalRepository.findByUsername(professional.getUsername()).isPresent()){
            throw new ProfessionalAlreadyExistsException("El profesional con email " + professional.getUsername() + " ya se encuentra registrado");

        }

        if(professionalRepository.findByMatricula(professional.getMatricula()).isPresent()){
            throw new ProfessionalAlreadyExistsException("El profesional con matricula " + professional.getMatricula() + " ya se encuentra registrado");
        }

        professional.setPassword(passwordEncoder.encode(professional.getPassword()));


        if (professionalDto.getIdSpecialityDtoList() == null || professionalDto.getIdSpecialityDtoList().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos una especialidad al profesional");
        }


        Set<SpecialityEntity> specialityEntities = specialityService.findSpecialitiesByID(professionalDto.getIdSpecialityDtoList());

        ProfessionalEntity professionalEntity = MapperProfessional.professionalModelToEntity(professional);

        professionalEntity.setSpecialities(specialityEntities);

        professionalRepository.save(professionalEntity);

    }

    @Override
    public ProfessionalResponse findProfessionalByID(long id) throws UserNotExistsException {
        ProfessionalEntity professionalEntity = professionalRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException("El profesional no se encuentra registrado"));

        return MapperProfessional.professionalEntityToResponse(professionalEntity);

    }

    public ProfessionalEntity findProfessionalEntityById(long idProfessional) throws UserNotExistsException {
        return professionalRepository.findById(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no existe"));
    }

    @Override
    public ProfessionalResponse findProfessionalByDni(long dniProfessional) throws UserNotExistsException {
        ProfessionalEntity professionalEntity = professionalRepository.findByDni(dniProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional con dni " + dniProfessional + " no se encontro"));

        return MapperProfessional.professionalEntityToResponse(professionalEntity);

    }

    @Override
    public List<Schedule> getProfesionalSchedule(long idProfessional) throws UserNotExistsException {

        ProfessionalEntity professionalEntity = professionalRepository.findByWithSchedule(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no existe"));

        Professional professional = MapperProfessional.professionalEntityToModel(professionalEntity);

        return professional.getSchedules();
    }


    @Override
    public List<ProfessionalResponse> getAllProfessionalBySpeciality(long idSpeciality) throws SpecialityNotExistsException {
        specialityService.findSpecialityById(idSpeciality);

        List<ProfessionalEntity> professionalEntities = professionalRepository.findAllWithSpecialities(idSpeciality);

        return professionalEntities.stream()
                .map(MapperProfessional::professionalEntityToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProfessionalResponse> getAllProfessionals(){
        List<ProfessionalEntity> professionalEntities = professionalRepository.findAll();

        return professionalEntities.stream()
                .map(MapperProfessional::professionalEntityToResponse)
                .collect(Collectors.toList());
    }

    public ProfessionalEntity getProfessionalWithScheduleEntity(long idProfessional) throws UserNotExistsException {

        return professionalRepository.findByWithSchedule(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no existe"));
    }

    @Override
    @Transactional
    public void deleteProfessional(long id) throws UserNotExistsException {
        ProfessionalEntity professionalEntity = professionalRepository.findByIdWithSpecialities(id)
                .orElseThrow(() -> new UserNotExistsException("El profesional no se encontro"));

        professionalRepository.delete(professionalEntity);
    }

    @Override
    @Transactional
    public void updateProfessionalData(long idProfessional, ProfessionalDto professionalDto) throws UserNotExistsException {

        ProfessionalEntity professionalEntity = professionalRepository.findById(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no se encontro"));


        professionalEntity.setDni(professionalDto.getDni());
        professionalEntity.setFirstName(professionalDto.getFirstName());
        professionalEntity.setLastName(professionalDto.getLastName());
        professionalEntity.setUsername(professionalDto.getUsername());
        professionalEntity.setMatricula(professionalDto.getMatricula());

        professionalRepository.save(professionalEntity);
    }

    @Transactional
    public void addNewSpecialityToTheProfessional(long idProfessional, long idSpeciality) throws UserNotExistsException, SpecialityNotExistsException, SpecialityAlreadyExistsException {
        ProfessionalEntity professionalEntity = professionalRepository.findByIdWithSpecialities(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no existe"));

        SpecialityEntity newSpeciality = specialityService.findSpecialityEntity(idSpeciality);

        Set<SpecialityEntity> currentSpecialities = professionalEntity.getSpecialities();

        boolean specialityAlreadyExists = currentSpecialities.stream().
                anyMatch(speciality -> speciality.equals(newSpeciality));

        if(specialityAlreadyExists){
            throw new SpecialityAlreadyExistsException("El profesional " +
                    professionalEntity.getLastName() +
                    " ya tiene registrada la especialidad " + newSpeciality);
        }

        currentSpecialities.add(newSpeciality);
        professionalRepository.save(professionalEntity);

    }

    @Transactional
    public void deleteSpecialityToTheProfessional(long idProfessional, long idSpeciality) throws UserNotExistsException, SpecialityNotExistsException {

        ProfessionalEntity professionalEntity = professionalRepository.findByIdWithSpecialities(idProfessional)
                .orElseThrow(() -> new UserNotExistsException("El profesional no existe"));

        SpecialityEntity deleteSpeciality = specialityService.findSpecialityEntity(idSpeciality);

        Set<SpecialityEntity> currentSpecialities = professionalEntity.getSpecialities();

        if(currentSpecialities.size() <= 1){
            throw new SpecialityNotExistsException("El profesional no puede tener menos de 1 especialidad");
        }

        boolean existsSpeciality = currentSpecialities.stream()
                .anyMatch(speciality -> speciality.equals(deleteSpeciality));

        if(!existsSpeciality){
            throw new SpecialityNotExistsException("El profesional " + professionalEntity.getLastName() +
                    " no tiene asignada la especialidad " + deleteSpeciality);
        }

        currentSpecialities.remove(deleteSpeciality);
        professionalRepository.save(professionalEntity);

    }

}
