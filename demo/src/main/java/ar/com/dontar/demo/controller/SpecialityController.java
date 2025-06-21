package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.SpecialityDto;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;
import ar.com.dontar.demo.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
public class SpecialityController {

    @Autowired
    SpecialityService specialityService;


    @GetMapping("/")
    public List<SpecialityResponse> getAllSpecialities(){
        return specialityService.getAllSpeciality();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/add")
    public ResponseEntity<Object> darAltaEspecialidad(@RequestBody SpecialityDto specialityDto) throws SpecialityNotExistsException, SpecialityAlreadyExistsException {
        specialityService.addNewSpeciality(specialityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Especialidad creada con exito");
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Object> deleteSpeciality(@PathVariable long Id) throws SpecialityNotExistsException {
        specialityService.deleteSpeciality(Id);
        return ResponseEntity.ok("Especialidad eliminada con exito");
    }
}
