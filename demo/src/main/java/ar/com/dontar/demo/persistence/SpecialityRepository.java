package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.persistence.entity.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<SpecialityEntity, Long> {

    Optional<SpecialityEntity> findByNameSpeciality(String nameSpeciality);
}
