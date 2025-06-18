package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.exception.AppoinmentNotGenerateException;
import ar.com.dontar.demo.mapper.ScheduleMapper;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.exception.ScheduleAlreadyExistsException;
import ar.com.dontar.demo.exception.ScheduleNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.persistence.ScheduleRepository;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import ar.com.dontar.demo.persistence.entity.ScheduleEntity;
import ar.com.dontar.demo.service.ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ProfessionalServiceImpl professionalService;

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Override
    @Transactional
    public void createSchedule(long idProfessional, List<ScheduleDto> scheduleDto)
            throws UserNotExistsException, ScheduleAlreadyExistsException, AppoinmentNotGenerateException, ScheduleNotExistsException {

        ProfessionalEntity professional = professionalService.getProfessionalWithScheduleEntity(idProfessional);

        List<Schedule> newSchedules = ScheduleMapper.scheduleDtoToModel(scheduleDto);

        for(Schedule schedule : newSchedules){
            if (schedule.getStartTime().isAfter(schedule.getEndTime())) {
                throw new ScheduleAlreadyExistsException("La hora de inicio no puede ser después de la hora de fin");
            }
        }

        List<Schedule> uniqueSchedule = newSchedules.stream().
                filter(newSchedule -> professional.getScheduleEntity().stream()
                        .noneMatch(s -> s.getDay().equals(newSchedule.getDay()) &&
                                isTimeRangeValid(s.getStartTime(), s.getEndTime(),
                                        newSchedule.getStartTime(), newSchedule.getEndTime()))
                ).toList();

        if (uniqueSchedule.isEmpty()) {
            throw new ScheduleAlreadyExistsException("Todos los horarios ya existen para el profesional " + professional.getLastName());
        }

        List<ScheduleEntity> scheduleEntities = uniqueSchedule.stream()
                .map(schedule -> {
                    ScheduleEntity entity = ScheduleMapper.scheduleModelToEntity(schedule);
                    entity.setProfessional(professional);
                    professional.addSchedule(entity);
                    return entity;
                })
                .toList();

        scheduleRepository.saveAll(scheduleEntities);

        appointmentService.appointmentGenerate(idProfessional);

    }


    private boolean isTimeRangeValid(LocalTime startTimeExists, LocalTime endTimeExists, LocalTime startTimeNew, LocalTime endTimeNew){
        return !(endTimeExists.isBefore(startTimeNew) || startTimeExists.isAfter(endTimeNew));

    }

    @Override
    @Transactional
    public void deleteProfessionalSchedule(long idProfessional, long idSchedule) throws UserNotExistsException, ScheduleNotExistsException {

        ProfessionalEntity professional = professionalService.getProfessionalWithScheduleEntity(idProfessional);

        ScheduleEntity schedules = professional.getScheduleEntity().stream()
                .filter(s -> s.getIdSchedule() == idSchedule)
                .findFirst()
                .orElseThrow(() -> new ScheduleNotExistsException("La agenda no existe"));

        DayOfWeek dayDelete = schedules.getDay();
        professional.getScheduleEntity().remove(schedules);
        scheduleRepository.deleteById(idSchedule);


        appointmentService.deleteAppointment(idProfessional, dayDelete);
    }

}
