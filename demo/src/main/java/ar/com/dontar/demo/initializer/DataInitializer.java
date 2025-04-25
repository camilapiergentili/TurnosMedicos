package ar.com.dontar.demo.initializer;

import ar.com.dontar.demo.model.UserType;
import ar.com.dontar.demo.persistence.AdministratorRepository;
import ar.com.dontar.demo.persistence.entity.AdministratorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(administratorRepository.findByUserType(UserType.ADMINISTRADOR).isEmpty()) {

            AdministratorEntity administrator = new AdministratorEntity();
            administrator.setDni(123456789);
            administrator.setFirstName("administrador");
            administrator.setLastName("dontar");
            administrator.setUserType(UserType.ADMINISTRADOR);
            administrator.setUsername("clinicadontar@hotmail.com");
            administrator.setPassword(passwordEncoder.encode("admin1234"));

            administratorRepository.save(administrator);

        }


    }
}
