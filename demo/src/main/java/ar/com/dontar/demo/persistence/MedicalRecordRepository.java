package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.persistence.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, Long> {

    @Query("SELECT m FROM MedicalRecordEntity m " +
            "WHERE m.professional.id = :idProfessional " +
            "AND m.patient.id = :idPatient " +
            "AND m.date = :today")
    Optional<MedicalRecordEntity> findMedicalRecordByPatientAndProfessionalToday(@Param("idPatient") long idPatient,
                                                                                 @Param("idProfessional") long idProfessional,
                                                                                 @Param("today")LocalDate today);


    @Query("SELECT m FROM MedicalRecordEntity m " +
            "JOIN FETCH m.professional " +
            "WHERE m.patient.id = :idPatient")
    List<MedicalRecordEntity> findAllMedicalRecordByPatient(@Param("idPatient") long idPatient);
}
