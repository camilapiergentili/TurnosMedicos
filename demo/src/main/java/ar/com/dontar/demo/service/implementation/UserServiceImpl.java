package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.model.User;
import ar.com.dontar.demo.model.exception.UserNoExistException;
import ar.com.dontar.demo.model.exception.IncorrectPaswordException;
import ar.com.dontar.demo.persistence.UserRepository;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import ar.com.dontar.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) throws UserNoExistException, IncorrectPaswordException {
        UserEntity userEntity = userRepository.findByEmail(email).
                orElseThrow(() -> new UserNoExistException("El usuario no se encuentra registrado"));

        User user = userEntity.userToModel();

        if (passwordEncoder.matches(password, user.getPassword())){
            return user;
        }else{
            throw new IncorrectPaswordException("Contraseña incorrecta");
        }
    }
}
