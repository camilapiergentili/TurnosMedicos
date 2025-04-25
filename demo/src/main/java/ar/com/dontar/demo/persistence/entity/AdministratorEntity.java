package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.Administrator;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrator")
@PrimaryKeyJoinColumn(name = "idUser")
public class AdministratorEntity extends UserEntity {

    public AdministratorEntity(){

    }

    public AdministratorEntity(Administrator administrator){
        super(administrator);
    }

}
