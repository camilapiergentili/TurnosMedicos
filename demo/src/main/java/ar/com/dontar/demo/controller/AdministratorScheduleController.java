package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.ScheduleDto;
import ar.com.dontar.demo.exception.ScheduleAlreadyExistsException;
import ar.com.dontar.demo.exception.ScheduleNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator-schedule")
public class AdministratorScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/add-schedule/{id}")
    public ResponseEntity<Object> addSchedule(@PathVariable long id, @RequestBody List<ScheduleDto> scheduleDto) throws UserNotExistsException, ScheduleAlreadyExistsException {
        scheduleService.createSchedule(id, scheduleDto);
        return ResponseEntity.ok("Agenda creada con exito");
    }

    @DeleteMapping("/delete/{idProfessional}/{idSchedule}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable long idProfessional, @PathVariable long idSchedule) throws ScheduleNotExistsException, UserNotExistsException {
        scheduleService.deleteProfessionalSchedule(idProfessional, idSchedule);
        return ResponseEntity.ok("Agenda eliminada con exito");
    }
}
