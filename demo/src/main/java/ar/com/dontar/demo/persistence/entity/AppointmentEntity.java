package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Appointment;
import ar.com.dontar.demo.model.AppointmentStatus;
import ar.com.dontar.demo.model.Patient;
import ar.com.dontar.demo.model.Professional;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAppointment;

    private LocalDate diaTurno;
    private LocalTime horarioTurno;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus estado;


    @ManyToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity paciente;

    @ManyToOne
    @JoinColumn(name = "id_professional", nullable = false)
    private ProfessionalEntity profesional;

    public AppointmentEntity(Appointment appointment, ProfessionalEntity professional, PatientEntity patient){
        this.diaTurno = appointment.getDiaTurno();
        this.horarioTurno = appointment.getHorarioTurno();
        this.estado = appointment.getEstado();
        this.paciente = patient;
        this.profesional = professional;
    }

    public Appointment appointmentToModel(){
        Appointment appointment = new Appointment();
        appointment.setDiaTurno(this.diaTurno);
        appointment.setHorarioTurno(this.horarioTurno);
        appointment.setEstado(this.estado);
        appointment.setPaciente(this.paciente.toModel());
        appointment.setProfesional(this.profesional.professionalToModel());

        return appointment;
    }




}
