package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.exception.ScheduleAlreadyExistsException;
import ar.com.dontar.demo.exception.ScheduleNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;

import java.util.List;

public interface ScheduleService {
    void createSchedule(long idProfessional, List<ScheduleDto> scheduleDto) throws UserNotExistsException, ScheduleAlreadyExistsException;
    void deleteProfessionalSchedule(long idProfessional, long idSchedule) throws UserNotExistsException, ScheduleNotExistsException;

}
