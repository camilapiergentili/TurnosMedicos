package ar.com.dontar.demo.mapper;

import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.persistence.entity.ScheduleEntity;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {

    public static List<Schedule> scheduleDtoToModel(List<ScheduleDto> scheduleDto){
        return scheduleDto.stream().map(dto -> {
            Schedule scheduleModel = new Schedule();
            try {
                scheduleModel.setDay(DayOfWeek.valueOf(dto.getDay().toUpperCase())); // Convertimos a mayúsculas
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Día inválido: " + dto.getDay()); // Manejo de error
            }
            scheduleModel.setStartTime(LocalTime.parse(dto.getStartTime()));
            scheduleModel.setEndTime(LocalTime.parse(dto.getEndTime()));
            return scheduleModel;
        }).collect(Collectors.toList());
    }


    public static Schedule scheduleEntityToModel(ScheduleEntity entity){
        Schedule schedule = new Schedule();
        schedule.setIdSchedule(entity.getIdSchedule());
        schedule.setDay(entity.getDay());
        schedule.setStartTime(entity.getStartTime());
        schedule.setEndTime(entity.getEndTime());

        return schedule;
    }

    public static ScheduleEntity scheduleModelToEntity(Schedule schedule){
        ScheduleEntity entity = new ScheduleEntity();
        entity.setDay(schedule.getDay());
        entity.setEndTime(schedule.getEndTime());
        entity.setStartTime(schedule.getStartTime());

        return entity;
    }
}
