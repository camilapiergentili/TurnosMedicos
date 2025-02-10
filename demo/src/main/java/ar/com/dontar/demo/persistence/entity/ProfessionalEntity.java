package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Professional")
@PrimaryKeyJoinColumn(name = "idUser")
public class ProfessionalEntity extends UserEntity{

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AppointmentEntity> appointmentsProfessional = new ArrayList<>();

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> scheduleEntity = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "professional_speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<SpecialityEntity> specialities = new ArrayList<>();

    private final String matricula;

    public ProfessionalEntity(Professional professional, List<SpecialityEntity> specialityEntities) {
        super(professional);
        this.matricula = professional.getMatricula();
        if(professional.getSchedules() != null || professional.getSchedules().isEmpty()){
            this.scheduleEntity = professional.getSchedules().stream()
                    .map(schedule -> new ScheduleEntity(schedule, this)) // 'this' es el ProfessionalEntity
                    .collect(Collectors.toList());

        }
        this.specialities = specialityEntities;

    }

    public Professional professionalToModel(){
        Professional professional = new Professional();
        professional.setDni(super.getDni());
        professional.setFirstName(super.getFirstName());
        professional.setLastName(super.getLastName());
        professional.setEmail(super.getEmail());
        professional.setPassword(super.getPassword());
        professional.setMatricula(this.matricula);

        return professional;
    }

    public List<AppointmentEntity> getAppointmentsProfessional() {
        return appointmentsProfessional;
    }

    public List<ScheduleEntity> getScheduleEntity() {
        return scheduleEntity;
    }

    public List<SpecialityEntity> getSpecialities() {
        return specialities;
    }

    public String getMatricula() {
        return matricula;
    }
}
