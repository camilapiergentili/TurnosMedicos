package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
    ProfessionalRepository findByDni(long dni);

}
