package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.SpecialityDto;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.mapper.MapperSpeciality;
import ar.com.dontar.demo.model.Speciality;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;
import ar.com.dontar.demo.persistence.SpecialityRepository;
import ar.com.dontar.demo.persistence.entity.SpecialityEntity;
import ar.com.dontar.demo.service.SpecialityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    SpecialityRepository specialityRepository;

    @Transactional
    public Set<SpecialityEntity> findSpecialitiesByID(Set<Long> specialities) throws SpecialityNotExistsException {

        List<SpecialityEntity> specialityEntities = specialityRepository.findAllById(specialities);

        if(specialityEntities.size() != specialities.size()){
            throw new SpecialityNotExistsException("Algunas de las especialidades no se encuentran registradas");
        }

        return new HashSet<>(specialityEntities);
    }


    public void findSpecialityById(long idSpeciality) throws SpecialityNotExistsException {
        SpecialityEntity specialityEntity = specialityRepository.findById(idSpeciality).
                orElseThrow(() -> new SpecialityNotExistsException("La especialidad no se encontro"));

        MapperSpeciality.specialityEntityToModel(specialityEntity);

    }

    public SpecialityEntity findSpecialityEntity(long idSpeciality) throws SpecialityNotExistsException {

        return specialityRepository.findById(idSpeciality).
                orElseThrow(() -> new SpecialityNotExistsException("La especialidad no se encontro"));
    }

    @Override
    @Transactional
    public void addNewSpeciality(SpecialityDto specialityDto) throws SpecialityAlreadyExistsException {

        Speciality speciality = MapperSpeciality.specialityDtoToModel(specialityDto);

        boolean exists = specialityRepository.findByNameSpeciality(speciality.getSpecialityName()).isPresent();

        if (exists) {
            throw new SpecialityAlreadyExistsException("La especialidad " + speciality.getSpecialityName() + " ya existe");
        }

        SpecialityEntity specialityEntity = MapperSpeciality.specialityModelToEntity(speciality);

        specialityRepository.save(specialityEntity);
    }

    @Override
    public void deleteSpeciality(long idSpeciality) throws SpecialityNotExistsException {
        SpecialityEntity speciality = specialityRepository.findById(idSpeciality)
                .orElseThrow(() -> new SpecialityNotExistsException("La especialidad no existe"));

        specialityRepository.delete(speciality);
    }

    @Override
    public List<SpecialityResponse> getAllSpeciality(){
        List<SpecialityEntity> specialities = specialityRepository.findAll();

        return specialities.stream()
                .map(MapperSpeciality::specialityEntityToResponse)
                .collect(Collectors.toList());
    }



}
