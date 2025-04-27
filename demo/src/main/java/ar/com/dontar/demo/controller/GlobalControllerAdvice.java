package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.exception.ExtractInfoUserFromTokenException;
import ar.com.dontar.demo.security.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    JwtUtilService jwtUtilService;

    @ModelAttribute("idUser")
    public Long extractUserIdFromToken(@RequestHeader("Authorization") String authHeader) throws ExtractInfoUserFromTokenException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExtractInfoUserFromTokenException("El token es inválido o no se proporcionó.");
        }
        return jwtUtilService.extractUserIdFromToken(authHeader.replace("Bearer ", ""));
    }

    @ModelAttribute("userType")
    public String extractUserTypeFromToken(@RequestHeader("Authorization") String authHeader) throws ExtractInfoUserFromTokenException {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new ExtractInfoUserFromTokenException("El token es invalido");
        }

        return jwtUtilService.extractRoleFromToken(authHeader.replace("Bearer ", ""));
    }
}
