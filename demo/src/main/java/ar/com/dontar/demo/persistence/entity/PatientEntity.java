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

    private final LocalDate dateOfBirth;
    private final String obraSocial;
    private final String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointmentsPatient = new ArrayList<>();

    public PatientEntity(Patient patient) {
        super(patient);
        this.dateOfBirth = patient.getDateOfBirth();
        this.obraSocial = patient.getObraSocial();
        this.phone = patient.getPhone();
    }

    public Patient toModel(){
        Patient patient = new Patient();
        patient.setDni(super.getDni());
        patient.setFirstName(super.getFirstName());
        patient.setLastName(super.getLastName());
        patient.setEmail(super.getEmail());
        patient.setPassword(super.getPassword());
        patient.setDateOfBirth(this.dateOfBirth);
        patient.setObraSocial(this.obraSocial);
        patient.setPhone(this.phone);

        return patient;
    }

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

    public void setAppointmentsPatient(List<AppointmentEntity> appointmentsPatient) {
        this.appointmentsPatient = appointmentsPatient;
    }
}
