package ar.com.dontar.demo.mapper;

import ar.com.dontar.demo.controller.dto.MedicalRecordDto;
import ar.com.dontar.demo.controller.response.MedicalRecordResponse;
import ar.com.dontar.demo.model.MedicalRecord;
import ar.com.dontar.demo.persistence.entity.MedicalRecordEntity;

import java.time.LocalDate;

public class MapperMedicalRecord {

    public static MedicalRecord medicalDtoToModel(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDate(LocalDate.parse(medicalRecordDto.getDate()));
        medicalRecord.setHistory(medicalRecordDto.getHistory());

        return medicalRecord;

    }

    public static MedicalRecordEntity medicalModelToEntity(MedicalRecord medicalRecord) {
        MedicalRecordEntity medicalEntity = new MedicalRecordEntity();
        medicalEntity.setDate(medicalRecord.getDate());
        medicalEntity.setHistory(medicalRecord.getHistory());

        return medicalEntity;
    }

    public static MedicalRecord medicalEntityToModel(MedicalRecordEntity medicalRecord){
        MedicalRecord medicalModel = new MedicalRecord();
        medicalModel.setDate(medicalRecord.getDate());
        medicalModel.setHistory(medicalRecord.getHistory());

        return medicalModel;
    }

    public static MedicalRecordResponse medicalEntityToResponse(MedicalRecordEntity medicalRecordEntity){
        MedicalRecordResponse medicalResponse = new MedicalRecordResponse();
        medicalResponse.setDate(String.valueOf(medicalRecordEntity.getDate()));
        medicalResponse.setHistory(medicalRecordEntity.getHistory());

        return medicalResponse;
    }
}
