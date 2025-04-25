package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.AppointmentDto;
import ar.com.dontar.demo.controller.response.AppointmentResponse;
import ar.com.dontar.demo.exception.*;
import ar.com.dontar.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/generate/{id}")
    public ResponseEntity<Object> generateAppointmentProfessional(@PathVariable long id) throws UserNotExistsException, ScheduleNotExistsException, AppoinmentNotGenerateException {
        appointmentService.appointmentGenerate(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Agenda creada con exito");
    }

    @GetMapping("/available/{Id}")
    public List<LocalTime> availableAppointment(@PathVariable long Id, @RequestParam String date) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException {
        return appointmentService.getAvailableAppointment(Id, date);
    }

    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMINISTRADOR')")
    @GetMapping("/unavailable")
    public List<LocalTime> unavailableAppointment(@RequestParam(required = false) Long idProfessional,
                                                  @RequestParam String date,
                                                  @ModelAttribute("idUser") Long idUser,
                                                  @ModelAttribute("userType") String userType) throws UserNotExistsException, AppoinmentNotGenerateException {


        if(userType.equals("ROLE_PROFESIONAL")){
            idProfessional = idUser;
        }else if (userType.equals("ROLE_ADMINISTRADOR") && idProfessional == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID de profesional.");
        }

        return appointmentService.getUnavailableAppointment(idProfessional, date);
    }

    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMINISTRADOR')")
    @PostMapping("/reservation")
    public ResponseEntity<Object> bookAppointment(@RequestBody AppointmentDto appointmentDto,
                                                  @ModelAttribute("idUser") Long idUser,
                                                  @ModelAttribute("userType") String userType) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException, SpecialityNotExistsException {
        if(userType.equals("ROLE_PACIENTE")){
            appointmentDto.setIdPatient(idUser);
        }

        appointmentService.bookAppointment(appointmentDto);
        return ResponseEntity.ok("turno asignado con exito");
    }


    @PreAuthorize("hasRole('PACIENTE')")
    @GetMapping("/patient")
    public List<AppointmentResponse> getAppointmentPatient(@ModelAttribute("idUser") Long idUser) throws UserNotExistsException {
        return appointmentService.getAppointmentsByPatient(idUser);

    }

    @PreAuthorize("hasRole('PACIENTE')")
    @DeleteMapping("/cancel/{idAppointment}")
    public ResponseEntity<Object> cancelAppointmentPatient(@ModelAttribute("idUser") Long idUser,
                                                           @PathVariable long idAppointment) throws UserNotExistsException, CancellationTimeExceededException, AppointmentNotExistsException {
        appointmentService.cancelAppointment(idUser, idAppointment);
        return ResponseEntity.ok("Turno cancelado con exito");
    }

}
