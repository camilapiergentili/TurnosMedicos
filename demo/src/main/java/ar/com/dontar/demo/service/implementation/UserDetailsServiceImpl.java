package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.PasswordChangeDto;
import ar.com.dontar.demo.exception.IncorrectPaswordException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.persistence.UserRepository;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }


    public void forgotPassaword(String username){

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String newPassword = String.valueOf(userEntity.getDni());

        userEntity.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(userEntity);

    }

    public void passwordNew(long idUser, PasswordChangeDto passwordChange) throws UserNotExistsException, IncorrectPaswordException {

        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotExistsException("El usuario no existe"));

        if(!passwordEncoder.matches(passwordChange.getOldPassword(), userEntity.getPassword())){
            throw new IncorrectPaswordException("Tu contraseña anterior no coincide");
        }

        userEntity.setPassword((passwordEncoder.encode(passwordChange.getPassword())));

        userRepository.save(userEntity);

    }
}
