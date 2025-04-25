package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.SpecialityDto;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;

import java.util.List;

public interface SpecialityService {
    void addNewSpeciality(SpecialityDto specialityDto) throws SpecialityAlreadyExistsException;
    void deleteSpeciality(long idSpeciality) throws SpecialityNotExistsException;
    List<SpecialityResponse> getAllSpeciality();

}
