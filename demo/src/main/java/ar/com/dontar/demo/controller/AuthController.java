package ar.com.dontar.demo.controller;


import ar.com.dontar.demo.controller.dto.ForgotPasswordDto;
import ar.com.dontar.demo.controller.response.AuthResponse;
import ar.com.dontar.demo.controller.dto.LoginRequest;
import ar.com.dontar.demo.exception.IncorrectPaswordException;
import ar.com.dontar.demo.exception.UserNotExistsException;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import ar.com.dontar.demo.security.JwtUtilService;
import ar.com.dontar.demo.service.implementation.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) throws IncorrectPaswordException, UserNotExistsException {
        try {

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            UserEntity user = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(user);
            String role = user.getUserType().toString();

            return ResponseEntity.ok(new AuthResponse(token, role));


        } catch (UsernameNotFoundException ex) {
            throw new UserNotExistsException("El usuario no se encuentra registrado");
        }
        catch (BadCredentialsException ex) {
           throw new IncorrectPaswordException("La contraseña es incorrecta");
        }
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto request) throws UserNotExistsException {
        userDetailsService.forgotPassaword(request.getUsername());
        return ResponseEntity.ok("Su contraseña ha sido reestablecida, es su numero de dni");
    }

}
