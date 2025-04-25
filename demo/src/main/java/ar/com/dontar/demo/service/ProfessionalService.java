package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.response.ProfessionalResponse;
import ar.com.dontar.demo.exception.ProfessionalAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.model.Schedule;

import java.util.List;

public interface ProfessionalService {
    void registerProfessional(ProfessionalDto professionalDto) throws ProfessionalAlreadyExistsException, SpecialityNotExistsException;
    ProfessionalResponse findProfessionalByID(long id) throws UserNotExistsException;
    ProfessionalResponse findProfessionalByDni(long dniProfessional) throws UserNotExistsException;
    void deleteProfessional(long id) throws UserNotExistsException;
    void updateProfessionalData(long idProfessional, ProfessionalDto professionalDto) throws UserNotExistsException;
    List<ProfessionalResponse> getAllProfessionalBySpeciality(long idSpeciality) throws SpecialityNotExistsException;
    List<ProfessionalResponse> getAllProfessionals();
    List<Schedule> getProfesionalSchedule(long idProfessional) throws UserNotExistsException;
    void addNewSpecialityToTheProfessional(long idProfessional, long idSpeciality) throws UserNotExistsException, SpecialityNotExistsException, SpecialityAlreadyExistsException;
    void deleteSpecialityToTheProfessional(long idProfessional, long idSpeciality) throws UserNotExistsException, SpecialityNotExistsException;
}
