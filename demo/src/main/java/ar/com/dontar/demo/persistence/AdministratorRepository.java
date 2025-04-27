package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.Administrator;
import ar.com.dontar.demo.model.UserType;
import ar.com.dontar.demo.persistence.entity.AdministratorEntity;
import ar.com.dontar.demo.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {

    Optional<AdministratorEntity> findByUserType(UserType userType);
}
