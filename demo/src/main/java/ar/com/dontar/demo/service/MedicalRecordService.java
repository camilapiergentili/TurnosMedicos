package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.MedicalRecordDto;
import ar.com.dontar.demo.controller.response.MedicalRecordResponse;
import ar.com.dontar.demo.exception.UserNotExistsException;

import java.util.List;

public interface MedicalRecordService {
    void addMedicalRecord(MedicalRecordDto medicalRecordDto) throws UserNotExistsException;
    List<MedicalRecordResponse> findMedicalRecordByDni(long dniPatient) throws UserNotExistsException;
}
