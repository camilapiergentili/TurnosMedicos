package ar.com.dontar.demo.controller.dto;

import ar.com.dontar.demo.validation.annotation.ValidString;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class ScheduleDto {

    @ValidString
    private String day;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$")
    private String startTime;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$")
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
