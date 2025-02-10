package ar.com.dontar.demo.model;

import ar.com.dontar.demo.controller.dto.AppointmentDto;

import java.time.LocalDate;
import java.time.LocalTime;


public class Appointment {

    private long idAppointment;
    private LocalDate diaTurno;
    private LocalTime horarioTurno;
    private AppointmentStatus estado;
    private Patient paciente;
    private Professional profesional;

    public Appointment(){

    }

    public Appointment(AppointmentDto appointmentDto){
        this.diaTurno = LocalDate.parse(appointmentDto.getDay());
        this.horarioTurno = LocalTime.parse(appointmentDto.getTimeAppointment());
    }

    public long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public LocalDate getDiaTurno() {
        return diaTurno;
    }

    public void setDiaTurno(LocalDate diaTurno) {
        this.diaTurno = diaTurno;
    }

    public LocalTime getHorarioTurno() {
        return horarioTurno;
    }

    public void setHorarioTurno(LocalTime horarioTurno) {
        this.horarioTurno = horarioTurno;
    }

    public AppointmentStatus getEstado() {
        return estado;
    }

    public void setEstado(AppointmentStatus estado) {
        this.estado = estado;
    }

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }

    public Professional getProfesional() {
        return profesional;
    }

    public void setProfesional(Professional profesional) {
        this.profesional = profesional;
    }
}
