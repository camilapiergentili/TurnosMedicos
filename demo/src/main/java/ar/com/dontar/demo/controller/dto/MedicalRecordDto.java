package ar.com.dontar.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MedicalRecordDto {

    @NotNull(message = "La fecha no puede estar vacia")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
    private String date;

    @NotBlank(message = "La descripción no puede estar vacia")
    private String history;
    private long idPatient;
    private long idProfessional;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }

    public long getIdProfessional() {
        return idProfessional;
    }

    public void setIdProfessional(long idProfessional) {
        this.idProfessional = idProfessional;
    }
}
