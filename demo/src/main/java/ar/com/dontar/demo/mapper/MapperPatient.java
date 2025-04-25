package ar.com.dontar.demo.mapper;

import ar.com.dontar.demo.controller.dto.PatientDto;
import ar.com.dontar.demo.controller.response.PatientResponse;
import ar.com.dontar.demo.model.Appointment;
import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.model.UserType;
import ar.com.dontar.demo.persistence.entity.PatientEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MapperPatient {


    public static Patient patientDtoToModel(PatientDto patientDto){
        Patient patient = new Patient();
        patient.setDni(patientDto.getDni());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setUsername(patientDto.getUsername());
        patient.setPassword(patientDto.getPassword());
        patient.setDateOfBirth(LocalDate.parse(patientDto.getDateOfBirth()));
        patient.setObraSocial(patientDto.getObraSocial());
        patient.setPhone(patientDto.getPhone());
        patient.setUserType(UserType.PACIENTE);

        return patient;

    }

    public static PatientEntity patientModelToEntity(Patient patient){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setDni(patient.getDni());
        patientEntity.setFirstName(patient.getFirstName());
        patientEntity.setLastName(patient.getLastName());
        patientEntity.setUsername(patient.getUsername());
        patientEntity.setPassword(patient.getPassword());
        patientEntity.setUserType(patient.getUserType());
        patient.setDateOfBirth(patient.getDateOfBirth());
        patientEntity.setDateOfBirth(patient.getDateOfBirth());
        patientEntity.setObraSocial(patient.getObraSocial());
        patientEntity.setPhone(patient.getPhone());

        return patientEntity;

    }

    public static Patient patientEntityToModel(PatientEntity patientEntity){
        Patient patientModel = new Patient();
        patientModel.setIdUser(patientEntity.getIdUser());
        patientModel.setDni(patientEntity.getDni());
        patientModel.setFirstName(patientEntity.getFirstName());
        patientModel.setLastName(patientEntity.getLastName());
        patientModel.setUsername(patientEntity.getUsername());
        patientModel.setPassword(patientEntity.getPassword());
        patientModel.setUserType(patientEntity.getUserType());
        patientModel.setDateOfBirth(patientEntity.getDateOfBirth());
        patientModel.setDateOfBirth(patientEntity.getDateOfBirth());
        patientModel.setObraSocial(patientEntity.getObraSocial());
        patientModel.setPhone(patientEntity.getPhone());

        if(patientEntity.getAppointmentsPatient() != null && !patientEntity.getAppointmentsPatient().isEmpty()){
            List<Appointment> appointmentsByPatient = patientEntity.getAppointmentsPatient().stream()
                    .map(MapperAppointment::appointmentEntityToModel)
                    .collect(Collectors.toList());
            patientModel.setAppointmentsPatient(appointmentsByPatient);
        }

        return patientModel;

    }

    public static PatientResponse patientEntityToResponse(PatientEntity patientEntity) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName(patientEntity.getFirstName());
        patientResponse.setLastName(patientEntity.getLastName());
        patientResponse.setUsername(patientEntity.getUsername());
        patientResponse.setDni(patientEntity.getDni());
        patientResponse.setPhone(patientEntity.getPhone());
        patientResponse.setObraSocial(patientEntity.getObraSocial());
        patientResponse.setDateOfBirth(patientEntity.getDateOfBirth());

        return patientResponse;

    }

}
