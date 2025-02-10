package ar.com.dontar.demo.controller.dto;

import ar.com.dontar.demo.model.Professional;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDto {

    private String day;
    private String startTime;
    private String endTime;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
