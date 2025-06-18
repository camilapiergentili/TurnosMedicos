package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.model.Professional;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MedicalRecord" )
public class MedicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private String history;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalEntity professional;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    public MedicalRecordEntity(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public ProfessionalEntity getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalEntity professional) {
        this.professional = professional;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
