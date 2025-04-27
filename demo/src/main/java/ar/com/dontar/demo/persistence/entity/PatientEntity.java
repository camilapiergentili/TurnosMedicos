package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Patient;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient")
@PrimaryKeyJoinColumn(name = "idUser")
public class PatientEntity extends UserEntity {

    private LocalDate dateOfBirth;
    private String obraSocial;
    private String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointmentsPatient = new ArrayList<>();

    public PatientEntity() {}

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public String getPhone() {
        return phone;
    }

    public List<AppointmentEntity> getAppointmentsPatient() {
        return appointmentsPatient;
    }

    public void setAppointmentsPatient(AppointmentEntity appointmentsPatient) {
        this.appointmentsPatient.add(appointmentsPatient);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
