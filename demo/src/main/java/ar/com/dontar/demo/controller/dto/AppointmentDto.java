package ar.com.dontar.demo.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;

public class AppointmentDto {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
    @FutureOrPresent(message = "El día del turno debe ser futuro")
    private String appointmentDay;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$")
    private String appointmentTime;
    private long idProfessional;
    private long idPatient;
    private long idSpeciality;
    private boolean firstAppointment;

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public long getIdProfessional() {
        return idProfessional;
    }

    public void setIdProfessional(long idProfessional) {
        this.idProfessional = idProfessional;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }

    public boolean isFirstAppointment() {
        return firstAppointment;
    }

    public void setFirstAppointment(boolean firstAppointment) {
        this.firstAppointment = firstAppointment;
    }

    public long getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(long idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentDay='" + appointmentDay + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", idProfessional=" + idProfessional +
                ", idPatient=" + idPatient +
                ", isFirstAppointment=" + firstAppointment +
                '}';
    }
}
