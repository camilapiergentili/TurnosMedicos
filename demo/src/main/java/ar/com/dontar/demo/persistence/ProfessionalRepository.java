package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.persistence.entity.PatientEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    @Query("SELECT p FROM ProfessionalEntity p LEFT JOIN FETCH p.specialities WHERE p.idUser = :idUser")
    Optional<ProfessionalEntity> findByIdWithSpecialities(@Param("idUser") long idUser);

    @Query("SELECT p FROM ProfessionalEntity p LEFT JOIN FETCH p.scheduleEntity WHERE p.idUser = :idUser")
    Optional<ProfessionalEntity> findByWithSchedule(@Param("idUser") long idUser);

    Optional<ProfessionalEntity> findByDni(long dni);

    Optional<ProfessionalEntity> findByMatricula(String matricula);

    Optional<ProfessionalEntity> findByUsername(String username);

    @Query("SELECT p FROM ProfessionalEntity p JOIN p.specialities s WHERE s.idSpeciality = :idSpeciality")
    List<ProfessionalEntity> findAllWithSpecialities(@Param("idSpeciality") long idSpeciality);

    @Query("SELECT p FROM ProfessionalEntity p LEFT JOIN FETCH p.appointmentsProfessional WHERE p.idUser = :idUser")
    Optional<ProfessionalEntity> findByIdWithAppointment(@Param("idUser") long idUser);

}
