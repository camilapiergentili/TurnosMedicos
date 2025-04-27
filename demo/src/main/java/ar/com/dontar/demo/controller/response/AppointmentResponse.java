package ar.com.dontar.demo.controller.response;

import ar.com.dontar.demo.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;


public class AppointmentResponse {

    private LocalDate dayAppointment;
    private LocalTime timeAppointment;
    private AppointmentStatus status;
    private String nameProfessional;
    private SpecialityResponse speciality;


    public LocalDate getDayAppointment() {
        return dayAppointment;
    }

    public void setDayAppointment(LocalDate dayAppointment) {
        this.dayAppointment = dayAppointment;
    }

    public LocalTime getTimeAppointment() {
        return timeAppointment;
    }

    public void setTimeAppointment(LocalTime timeAppointment) {
        this.timeAppointment = timeAppointment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNameProfessional() {
        return nameProfessional;
    }

    public void setNameProfessional(String nameProfessional) {
        this.nameProfessional = nameProfessional;
    }

    public SpecialityResponse getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityResponse speciality) {
        this.speciality = speciality;
    }
}
