package ar.com.dontar.demo.model;

import ar.com.dontar.demo.controller.dto.ScheduleDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    @JsonIgnore
    private int idSchedule;

    @JsonIgnore
    private Professional professional;

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public Schedule(){}

    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
