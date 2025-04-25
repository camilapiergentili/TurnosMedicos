package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.response.ProfessionalResponse;
import ar.com.dontar.demo.exception.*;
import ar.com.dontar.demo.model.Schedule;
import ar.com.dontar.demo.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    ProfessionalService professionalService;

    @PreAuthorize("hasRole('PROFESIONAL')")
    @GetMapping("my-profile")
   public ResponseEntity<ProfessionalResponse> profileProfessional(@ModelAttribute("idUser") Long idUser) throws UserNotExistsException {
        return ResponseEntity.ok(professionalService.findProfessionalByID(idUser));
    }

    @GetMapping("/speciality/{IdSpeciality}")
    public List<ProfessionalResponse> getProfessionalsBySpeciality(@PathVariable long IdSpeciality) throws SpecialityNotExistsException {
        return professionalService.getAllProfessionalBySpeciality(IdSpeciality);
    }

    @GetMapping("/getSchedule/{Id}")
    public List<Schedule> getSchedule(@PathVariable long Id) throws UserNotExistsException {
        return professionalService.getProfesionalSchedule(Id);
    }

    @GetMapping("/all-professionals")
    public List<ProfessionalResponse> getAllProfessionals(){
        return professionalService.getAllProfessionals();
    }
}
