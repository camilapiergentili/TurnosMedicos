package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.MedicalRecordDto;
import ar.com.dontar.demo.controller.response.MedicalRecordResponse;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<Object> addMedicalRecord(@RequestBody @Valid MedicalRecordDto medicalRecordDto) throws UserNotExistsException {
        medicalRecordService.addMedicalRecord(medicalRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "El historial se agrego correctamente"));
    }

    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{dniPatient}")
    public List<MedicalRecordResponse> findMedicalRecordByDni(@PathVariable long dniPatient) throws UserNotExistsException {

        return medicalRecordService.findMedicalRecordByDni(dniPatient);
    }
}
