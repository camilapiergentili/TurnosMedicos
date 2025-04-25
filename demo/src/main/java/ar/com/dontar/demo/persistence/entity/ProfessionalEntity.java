package ar.com.dontar.demo.persistence.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Professional")
@PrimaryKeyJoinColumn(name = "idUser")
public class ProfessionalEntity extends UserEntity {

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentEntity> appointmentsProfessional = new ArrayList<>();

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> scheduleEntity = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "professional_speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<SpecialityEntity> specialities = new HashSet<>();

    private String matricula;

    public ProfessionalEntity() {}

    public void setSpecialities(Set<SpecialityEntity> specialities) {
        for (SpecialityEntity speciality : specialities) {
            this.addSpeciality(speciality);
        }
    }

    public void addSpeciality(SpecialityEntity speciality) {
        if (!this.specialities.contains(speciality)) {
            this.specialities.add(speciality);
            speciality.getProfessionalSpeciality().add(this);
        }
    }

    public void addSchedule(ScheduleEntity scheduleEntity){
        this.scheduleEntity.add(scheduleEntity);
    }

    public List<ScheduleEntity> getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(List<ScheduleEntity> schedule) {
        this.scheduleEntity = schedule;
    }

    public List<AppointmentEntity> getAppointmentsProfessional() {
        return appointmentsProfessional;
    }

    public void setAppointmentsProfessional(List<AppointmentEntity> appointmentsProfessional) {
        this.appointmentsProfessional.addAll(appointmentsProfessional);
    }

    public Set<SpecialityEntity> getSpecialities() {
        return specialities;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
