package ar.com.dontar.demo.model;
import ar.com.dontar.demo.controller.dto.ProfessionalDto;

import java.util.ArrayList;
import java.util.List;

public class Professional extends User {

    private List<Appointment> appointmentsProfessional;
    private List<Specialty> specialties;
    private List<Schedule> schedules;
    private String matricula;

    public Professional(){
        this.appointmentsProfessional = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.specialties = new ArrayList<>();
    }


    public List<Appointment> getAppointmentsProfessional() {
        return appointmentsProfessional;
    }

    public void setAppointmentsProfessional(List<Appointment> appointmentsProfessional) {
        this.appointmentsProfessional = appointmentsProfessional;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
