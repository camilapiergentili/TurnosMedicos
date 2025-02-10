package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.model.User;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPassword(String password);
    Optional<UserEntity> findByDni(long dni);

}
