package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Specialty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSpeciality;

    private final String nameSpeciality;

    @ManyToMany(mappedBy = "specialities")
    private final List<ProfessionalEntity> professionalSpeciality = new ArrayList<>();

    public SpecialityEntity(Specialty specialty){
        this.nameSpeciality = specialty.getSpecialityName();
    }

    public Specialty SpecialityToModel(){
        Specialty specialty = new Specialty();
        specialty.setIdSpeciality(this.idSpeciality);
        specialty.setSpecialityName(this.nameSpeciality);

        return specialty;
    }


}
