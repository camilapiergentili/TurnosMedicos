package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.PatientDto;
import ar.com.dontar.demo.controller.response.PatientResponse;
import ar.com.dontar.demo.exception.EmailAlreadyRegisteredException;
import ar.com.dontar.demo.exception.PatientAlreadyExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.service.PatientService;
import ar.com.dontar.demo.validation.validator.OnCreate;
import ar.com.dontar.demo.validation.validator.OnUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;


    @PostMapping("/register")
    public ResponseEntity<Object> registerPatient(@RequestBody @Validated(OnCreate.class) PatientDto patientDto)
            throws PatientAlreadyExistsException, EmailAlreadyRegisteredException {
        patientService.signUp(patientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Paciente registrado con éxito"));
    }

    @PreAuthorize("hasRole('PACIENTE')")
    @GetMapping("/my-profile")
    public ResponseEntity<PatientResponse> profilePatient(@ModelAttribute("idUser") Long idUser)
            throws UserNotExistsException {
        return ResponseEntity.ok(patientService.findPatientById(idUser));
    }

    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMINISTRADOR')")
    @GetMapping("/find-by-dni/{dniPatient}")
    public ResponseEntity<PatientResponse> findPatientByDni(@PathVariable long dniPatient) throws UserNotExistsException {
        return ResponseEntity.ok(patientService.findPatientByDni(dniPatient));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/get-all")
    public List<PatientResponse> getAllPatient(){
        return patientService.findAllPatient();
    }


    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMINISTRADOR')")
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestParam(value="id", required = false) Long idPatient,
                                         @ModelAttribute("idUser") Long idUser,
                                         @ModelAttribute("userType") String userType,
                                         @RequestBody @Validated(OnUpdate.class) PatientDto patientDto) throws UserNotExistsException {

        if (userType.equals("ROLE_PACIENTE")) {
            idPatient = idUser;
        } else if (userType.equals("ROLE_PROFESIONAL") && idPatient == null) {
            throw new IllegalArgumentException("Debe proporcionar el id del paciente");
        }

        patientService.updatePatient(idPatient, patientDto);
        return ResponseEntity.ok(Map.of("message", "Paciente actualizado con éxito"));
    }

    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam(value= "id", required = false) Long idPatient,
                                         @ModelAttribute("idUser") Long idUser,
                                         @ModelAttribute("userType") String userType) throws UserNotExistsException {
        if (userType.equals("ROLE_PACIENTE")) {
            idPatient = idUser;
        } else if (userType.equals("ROLE_PROFESIONAL") && idPatient == null) {
            throw new IllegalArgumentException("Debe proporcionar el id del paciente");
        }

        patientService.detelePatient(idPatient);
        return ResponseEntity.ok(Map.of("message", "Paciente eliminado con éxito"));
    }
}
