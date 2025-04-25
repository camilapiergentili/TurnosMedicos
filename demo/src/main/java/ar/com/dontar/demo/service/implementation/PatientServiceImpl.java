package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.PatientDto;
import ar.com.dontar.demo.controller.response.PatientResponse;
import ar.com.dontar.demo.exception.EmailAlreadyRegisteredException;
import ar.com.dontar.demo.mapper.MapperPatient;
import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.exception.PatientAlreadyExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.persistence.PatientRepository;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // This method is used to register the patient
    @Override
    public void signUp(PatientDto patientDto) throws PatientAlreadyExistsException, EmailAlreadyRegisteredException {
        Patient patient = MapperPatient.patientDtoToModel(patientDto);

        if(patientRepository.findByDni(patient.getDni()) != null){
            throw new PatientAlreadyExistsException("El paciente con dni " + patient.getDni() + " ya esta registrado");
        }

        if(patientRepository.findByUsername(patient.getUsername()) != null) {
            throw new EmailAlreadyRegisteredException("El email " + patient.getUsername() + " ya se encuentra en uso");
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        PatientEntity patientEntity = MapperPatient.patientModelToEntity(patient);

        patientRepository.save(patientEntity);

    }

    //This method is to search for one patient by dni
    @Override
    public PatientResponse findPatientByDni(long dni) throws UserNotExistsException {

        PatientEntity patientEntity = patientRepository.findByDni(dni);

        if(patientEntity == null){
            throw new UserNotExistsException("El paciente con dni " + dni + " no se encuentra registrado");
        }

        return MapperPatient.patientEntityToResponse(patientEntity);

    }

    @Override
    public PatientResponse findPatientById(long idPatient) throws UserNotExistsException {

        PatientEntity patientEntity = patientRepository.findById(idPatient)
                .orElseThrow(() -> new UserNotExistsException("El paciente no se encuentro"));

        return MapperPatient.patientEntityToResponse(patientEntity);

    }

    public PatientEntity findPatientEntityById(long idPatient) throws UserNotExistsException {

        return patientRepository.findById(idPatient)
                .orElseThrow(() -> new UserNotExistsException("El paciente no se encuentro"));

    }

    public PatientEntity findPatientWithAppointment(long idPatient) throws UserNotExistsException {

        return patientRepository.findByIdWithAppointment(idPatient)
                .orElseThrow(() -> new UserNotExistsException("El paciente no se encontro"));
    }

    @Override
    public void updatePatient(long idPatient, PatientDto patientDto) throws UserNotExistsException {

        PatientEntity patientEntity = patientRepository.findById(idPatient)
                .orElseThrow(() -> new UserNotExistsException("El paciente no exite"));

        patientEntity.setDni(patientDto.getDni());
        patientEntity.setFirstName(patientDto.getFirstName());
        patientEntity.setLastName(patientDto.getLastName());
        patientEntity.setUsername(patientDto.getUsername());
        patientEntity.setPassword(passwordEncoder.encode(patientDto.getPassword()));
        patientEntity.setDateOfBirth(LocalDate.parse(patientDto.getDateOfBirth()));
        patientEntity.setPhone(patientDto.getPhone());
        patientEntity.setObraSocial(patientDto.getObraSocial());

        patientRepository.save(patientEntity);
    }

    @Override
    public void detelePatient(long idPatient) throws UserNotExistsException {
        PatientEntity patientEntity = patientRepository.findById(idPatient)
                .orElseThrow(() -> new UserNotExistsException("El paciente no exite"));

        patientRepository.delete(patientEntity);
    }

    @Override
    public List<PatientResponse> findAllPatient(){
        List<PatientEntity> allPatients = patientRepository.findAll();

        return allPatients.stream()
                .map(MapperPatient::patientEntityToResponse)
                .collect(Collectors.toList());
    }

}
