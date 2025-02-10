package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    PatientEntity findByDni(long dni);
}
