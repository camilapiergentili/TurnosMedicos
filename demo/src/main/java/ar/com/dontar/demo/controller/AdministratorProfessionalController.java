package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.controller.dto.ProfessionalDto;
import ar.com.dontar.demo.controller.response.ProfessionalResponse;
import ar.com.dontar.demo.exception.ProfessionalAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityAlreadyExistsException;
import ar.com.dontar.demo.exception.SpecialityNotExistsException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/administrator-professional")
public class AdministratorProfessionalController {

    @Autowired
    ProfessionalService professionalService;

    @PostMapping("/register-professional")
    public ResponseEntity<Object> registerProfessinal(@RequestBody ProfessionalDto professionalDto) throws ProfessionalAlreadyExistsException, SpecialityNotExistsException {
        professionalService.registerProfessional(professionalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profesional registrado con éxito");
    }

    @GetMapping("/find-by-dni/{dniProfessional}")
    public ResponseEntity<ProfessionalResponse> findProfessionalByDni(@PathVariable long dniProfessional) throws UserNotExistsException {
        return ResponseEntity.ok(professionalService.findProfessionalByDni(dniProfessional));
    }

    @PutMapping("/update-data-professional/{Id}")
    public ResponseEntity<Object> updateDataProfessional(@PathVariable long Id, @RequestBody ProfessionalDto professionalDto) throws UserNotExistsException {
        professionalService.updateProfessionalData(Id, professionalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profesional modificado con exito");
    }

    @PostMapping("/add-new-speciality/{idProfessional}/{idSpeciality}")
    public ResponseEntity<Object> addNewSpecialityProfessional(@PathVariable long idProfessional, @PathVariable long idSpeciality) throws SpecialityNotExistsException, UserNotExistsException, SpecialityAlreadyExistsException {
        professionalService.addNewSpecialityToTheProfessional(idProfessional, idSpeciality);
        return ResponseEntity.ok("Especialidad agregada a el profesional con exito");
    }

    @DeleteMapping("/delete-speciality/{idProfessional}/{idSpeciality}")
    public ResponseEntity<Object> deleteSpeciality(@PathVariable long idProfessional, @PathVariable long idSpeciality) throws SpecialityNotExistsException, UserNotExistsException {
        professionalService.deleteSpecialityToTheProfessional(idProfessional, idSpeciality);
        return ResponseEntity.ok("La especialidad al profesional eliminada con exito");
    }

    @DeleteMapping("/delete-professional/{Id}")
    public ResponseEntity<Object> deleteProfessiona(@PathVariable long Id) throws UserNotExistsException {
        professionalService.deleteProfessional(Id);
        return ResponseEntity.ok("El profesional se elimino correctamente");
    }

}
