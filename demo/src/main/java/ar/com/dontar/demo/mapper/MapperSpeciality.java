package ar.com.dontar.demo.mapper;

import ar.com.dontar.demo.controller.dto.SpecialityDto;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.model.Speciality;
import ar.com.dontar.demo.persistence.entity.SpecialityEntity;

public class MapperSpeciality {

    public static Speciality specialityDtoToModel(SpecialityDto specialityDto) {

        String nameNewSpeciality = specialityDto.getNameSpeciality().toUpperCase();
        Speciality speciality = new Speciality();
        speciality.setSpecialityName(nameNewSpeciality);

        return speciality;
    }

    public static Speciality specialityEntityToModel(SpecialityEntity specialityEntity) {
        Speciality specialty = new Speciality();
        specialty.setIdSpeciality(specialityEntity.getIdSpeciality());
        specialty.setSpecialityName(specialityEntity.getNameSpeciality());

        return specialty;
    }

    public static SpecialityEntity specialityModelToEntity(Speciality speciality) {
        SpecialityEntity specialityEntity = new SpecialityEntity();
        specialityEntity.setNameSpeciality(speciality.getSpecialityName());

        return specialityEntity;
    }

    public static SpecialityResponse specialityEntityToResponse(SpecialityEntity specialityEntity) {
        SpecialityResponse specialityResponse = new SpecialityResponse();
        specialityResponse.setIdSpeciality(specialityEntity.getIdSpeciality());
        specialityResponse.setName(specialityEntity.getNameSpeciality());

        return specialityResponse;
    }
}
