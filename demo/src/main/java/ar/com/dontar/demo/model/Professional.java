package ar.com.dontar.demo.model;
import ar.com.dontar.demo.controller.dto.ProfessionalDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Professional extends Usuario {

    private List<Appointment> appointmentsProfessional;
    private Set<Speciality> specialties;
    private List<Schedule> schedules;
    private String matricula;

    public Professional(){
        this.appointmentsProfessional = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.specialties = new HashSet<>();
    }

    public Set<Speciality> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Speciality> specialties) {
        this.specialties = specialties;
    }

    public List<Appointment> getAppointmentsProfessional() {
        return appointmentsProfessional;
    }

    public void setAppointmentsProfessional(List<Appointment> appointmentsProfessional) {
        this.appointmentsProfessional = appointmentsProfessional;
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
