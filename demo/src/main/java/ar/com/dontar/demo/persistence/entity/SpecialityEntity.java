package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Speciality;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Speciality")
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSpeciality;

    private String nameSpeciality;

    @ManyToMany(mappedBy = "specialities")
    private List<ProfessionalEntity> professionalSpeciality = new ArrayList<>();

    public SpecialityEntity() {
    }

    public void addProfessional(ProfessionalEntity professional) {
        this.professionalSpeciality.add(professional);
    }

    public void setNameSpeciality(String nameSpeciality) {
        this.nameSpeciality = nameSpeciality;
    }

    public long getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(long idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getNameSpeciality() {
        return nameSpeciality;
    }

    public List<ProfessionalEntity> getProfessionalSpeciality() {
        return professionalSpeciality;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Si es el mismo objeto, retorna true
        if (obj == null || getClass() != obj.getClass()) return false; // Si no son de la misma clase, retorna false
        SpecialityEntity that = (SpecialityEntity) obj;
        return Objects.equals(idSpeciality, that.idSpeciality); // Compara los ID
    }

    // Implementación de hashCode() basada en el ID
    @Override
    public int hashCode() {
        return Objects.hash(idSpeciality);
    }



}
