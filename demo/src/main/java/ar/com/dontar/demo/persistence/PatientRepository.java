package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    PatientEntity findByDni(long dni);

    PatientEntity findByUsername(String username);

    @Query("SELECT p FROM PatientEntity p LEFT JOIN FETCH p.appointmentsPatient WHERE p.idUser = :idUser")
    Optional<PatientEntity> findByIdWithAppointment(@Param("idUser") long idUser);
}
