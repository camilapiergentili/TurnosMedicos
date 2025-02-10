package ar.com.dontar.demo.controller.dto;

public class AppointmentDto {

    private String day;
    private String timeAppointment;
    private long idProfessional;
    private long idPatient;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getIdProfessional() {
        return idProfessional;
    }

    public void setIdProfessional(long idProfessional) {
        this.idProfessional = idProfessional;
    }

    public String getTimeAppointment() {
        return timeAppointment;
    }

    public void setTimeAppointment(String timeAppointment) {
        this.timeAppointment = timeAppointment;
    }

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }
}
