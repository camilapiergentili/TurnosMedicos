package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.PatientDto;
import ar.com.dontar.demo.controller.response.PatientResponse;
import ar.com.dontar.demo.exception.EmailAlreadyRegisteredException;
import ar.com.dontar.demo.exception.PatientAlreadyExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;

import java.util.List;

public interface PatientService {

    void signUp(PatientDto patientDto) throws PatientAlreadyExistsException, EmailAlreadyRegisteredException;
    PatientResponse findPatientByDni(long dni) throws UserNotExistsException;
    PatientResponse findPatientById(long idPatient) throws UserNotExistsException;
    void updatePatient(long idPatient, PatientDto patientDto) throws UserNotExistsException;
    void detelePatient(long idPatient) throws UserNotExistsException;
    List<PatientResponse> findAllPatient();

}
