package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.persistence.entity.AppointmentEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE AppointmentEntity a SET a.appointmentStatus = 'EXPIRED' " +
            "WHERE FUNCTION('TIMESTAMP', a.appointmentDay, a.appointmentTime) < CURRENT_TIMESTAMP " +
            "AND a.appointmentStatus != 'EXPIRED'")
    void markPastAppointmentsAsExpired();

    boolean existsByProfessionalAndAppointmentDayAndAppointmentTime(
            ProfessionalEntity professional, LocalDate appointmentDay, LocalTime appointmentTime
    );

    @Query("SELECT a.appointmentTime FROM AppointmentEntity a " +
            "WHERE a.professional.id = :idProfessional " +
            "AND a.appointmentDay = :date " +
            "AND a.appointmentStatus = 'DISPONIBLE'")
    List<LocalTime> getAvailableAppointmentTimesForProfessional(@Param("idProfessional") long idProfessional,
                                                            @Param("date") LocalDate date);


    @Query("SELECT a.appointmentTime FROM AppointmentEntity a " +
            "WHERE a.professional.id = :idProfessional " +
            "AND a.appointmentDay = :date " +
            "AND a.appointmentStatus = 'RESERVADO'")
    List<LocalTime> getUnavailableAppointmentTimesForProfessional(@Param("idProfessional") long idProfessional,
                                                                  @Param("date") LocalDate date);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.professional.id = :idProfessional " +
            "AND a.appointmentStatus = 'RESERVADO'")
    List<AppointmentEntity> findUnavailableAppointmentByProfessional(@Param("idProfessional") long idProfessional);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.professional.id = :idProfessional " +
            "AND a.appointmentStatus = 'DISPONIBLE'")
    List<AppointmentEntity> findAvailableAppointmentByProfessional(@Param("idProfessional") long idProfessional);

    @Query("SELECT a FROM AppointmentEntity a " +
            "WHERE a.professional.id = :idProfessional " +
            "AND a.appointmentDay = :date " +
            "AND a.appointmentTime = :nextTime " +
            "AND a.patient.id = :idPatient " +
            "AND a.appointmentStatus = 'RESERVADO'")
    Optional<AppointmentEntity> findNextAppointmentPatient(
            @Param("idProfessional") long idProfessional,
            @Param("date") LocalDate date,
            @Param("nextTime") LocalTime nextTime,
            @Param("idPatient") long idPatient
    );


}
