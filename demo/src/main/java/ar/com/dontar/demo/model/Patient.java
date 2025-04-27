package ar.com.dontar.demo.model;

import ar.com.dontar.demo.controller.dto.PatientDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Patient extends Usuario {

    private LocalDate dateOfBirth;
    private String obraSocial;
    private String phone;
    private List<Appointment> appointmentsPatient;

    public Patient(){
        this.appointmentsPatient = new ArrayList<>();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Appointment> getAppointmentsPatient() {
        return appointmentsPatient;
    }

    public void setAppointmentsPatient(List<Appointment> appointmentsPatient) {
        this.appointmentsPatient = appointmentsPatient;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
