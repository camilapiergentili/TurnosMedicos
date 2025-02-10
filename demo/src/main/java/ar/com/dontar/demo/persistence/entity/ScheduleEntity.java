package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Professional;
import ar.com.dontar.demo.model.Schedule;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ScheduleProfessional")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSchedule;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalEntity professional;

    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduleEntity(Schedule schedule, ProfessionalEntity professionalEntity){
        this.day = schedule.getDay();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.professional = professionalEntity;
    }

    public Schedule scheduleToModel(){
        Schedule schedule = new Schedule();
        schedule.setIdSchedule(this.idSchedule);
        schedule.setDay(this.day);
        schedule.setStartTime(this.startTime);
        schedule.setEndTime(this.endTime);
        schedule.setProfessional(this.professional.professionalToModel());

        return schedule;
    }
}
