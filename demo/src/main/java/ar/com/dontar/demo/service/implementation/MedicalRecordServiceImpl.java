package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.MedicalRecordDto;
import ar.com.dontar.demo.controller.response.MedicalRecordResponse;
import ar.com.dontar.demo.exception.InvalidMedicalRecordDateException;
import ar.com.dontar.demo.exception.MedicalRecordAlreadyExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.mapper.MapperMedicalRecord;
import ar.com.dontar.demo.model.MedicalRecord;
import ar.com.dontar.demo.persistence.MedicalRecordRepository;
import ar.com.dontar.demo.persistence.entity.MedicalRecordEntity;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import ar.com.dontar.demo.service.MedicalRecordService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    ProfessionalServiceImpl professionalService;

    @Autowired
    PatientServiceImpl patientService;

    //metodo que pueden ver profesional y administrador
    @Override
    @Transactional
    public void addMedicalRecord(MedicalRecordDto medicalRecordDto) throws UserNotExistsException {

        MedicalRecord medicalRecord = MapperMedicalRecord.medicalDtoToModel(medicalRecordDto);
        PatientEntity patientEntity = patientService.findPatientEntityById(medicalRecordDto.getIdPatient());
        ProfessionalEntity professionalEntity = professionalService.findProfessionalEntityById(medicalRecordDto.getIdProfessional());

        if(medicalRecord.getDate().isAfter(LocalDate.now())){
            throw new InvalidMedicalRecordDateException("La fecha no puede ser futura");
        }

        LocalDate today = medicalRecord.getDate();

        if(medicalRecordRepository.findMedicalRecordByPatientAndProfessionalToday(patientEntity.getIdUser(),
                professionalEntity.getIdUser(), today).isPresent()){
            throw new MedicalRecordAlreadyExistsException("El Dr/a " + professionalEntity.getLastName()
                    + " ya realizo una prescripción a el/la paciente " + patientEntity.getFirstName()
                    + " " + patientEntity.getLastName() + " en el día de la fecha");
        }

        MedicalRecordEntity medicalRecordEntity = MapperMedicalRecord.medicalModelToEntity(medicalRecord);

        medicalRecordEntity.setPatient(patientEntity);
        medicalRecordEntity.setProfessional(professionalEntity);

        medicalRecordRepository.save(medicalRecordEntity);
    }

    @Override
    public List<MedicalRecordResponse> findMedicalRecordByDni(long dniPatient) throws UserNotExistsException {

        PatientEntity patientEntity = patientService.findPatientEntityByDni(dniPatient);
        List<MedicalRecordEntity> medicalRecordEntity = medicalRecordRepository.findAllMedicalRecordByPatient(patientEntity.getIdUser());

        return medicalRecordEntity.stream()
                .map(medical -> {
                    MedicalRecordResponse response = MapperMedicalRecord.medicalEntityToResponse(medical);
                    response.setNameProfessional(medical.getProfessional().getFirstName() + " " + medical.getProfessional().getLastName());
                    response.setNamePatient(patientEntity.getFirstName() + " " + patientEntity.getLastName());

                    return response;
                })
                .toList();
    }
}
