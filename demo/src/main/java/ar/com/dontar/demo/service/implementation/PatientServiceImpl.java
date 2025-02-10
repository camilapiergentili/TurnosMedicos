package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.PatientDto;
import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.model.exception.PatientAlreadyExistsException;
import ar.com.dontar.demo.model.exception.UserNoExistException;
import ar.com.dontar.demo.persistence.PatientRepository;
import ar.com.dontar.demo.persistence.UserRepository;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // This method is used to register the patient
    public void signUp(PatientDto patientDto) throws PatientAlreadyExistsException {
        Patient patient = new Patient(patientDto);

        if(patientRepository.findByDni(patient.getDni()) != null){
            throw new PatientAlreadyExistsException("El paciente con dni " + patient.getDni() + " ya esta registrado");
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        PatientEntity patientEntity = new PatientEntity(patient);

        patientRepository.save(patientEntity);

    }

    //This method is to search for one patient by dni
    public Patient findPatientByDni(long dni) throws UserNoExistException {

        PatientEntity patientEntity = patientRepository.findByDni(dni);

        if(patientEntity == null){
            throw new UserNoExistException("El paciente con dni " + dni + " no se encuentra registrado");
        }

        return patientEntity.toModel();

    }

    public void updatePatient(PatientDto patientDto) throws UserNoExistException {

        Patient patient = findPatientByDni(patientDto.getDni());

        PatientEntity patientUpdated = new PatientEntity(patient);

        patientRepository.save(patientUpdated);

    }

    public void requestAppointment(){

    }

}
