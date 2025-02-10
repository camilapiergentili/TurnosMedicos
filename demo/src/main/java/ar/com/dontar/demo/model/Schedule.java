package ar.com.dontar.demo.model;

import ar.com.dontar.demo.controller.dto.ScheduleDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    private int idSchedule;
    private Professional professional;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;

    public Schedule(){

    }

    public Schedule(ScheduleDto scheduleDto, Professional professional){
        this.day = LocalDate.parse(scheduleDto.getDay());
        this.startTime = LocalTime.parse(scheduleDto.getStartTime());
        this.endTime = LocalTime.parse(scheduleDto.getEndTime());
        this.professional = professional;
    }

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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
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
