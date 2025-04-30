package ar.com.dontar.demo.controller;

import ar.com.dontar.demo.configuration.PublicRoutesConfig;
import ar.com.dontar.demo.exception.ExtractInfoUserFromTokenException;
import ar.com.dontar.demo.security.JwtUtilService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    JwtUtilService jwtUtilService;

    @ModelAttribute("idUser")
    public Long extractUserIdFromToken(HttpServletRequest request) throws ExtractInfoUserFromTokenException {
        String path = request.getRequestURI();

        // Si es ruta pública, no intentes extraer el token
        if (PublicRoutesConfig.PUBLIC_ROUTES.contains(path)) {
            return null; // O podés tirar una excepción, o devolver un valor especial
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExtractInfoUserFromTokenException("El token es inválido o no se proporcionó.");
        }

        return jwtUtilService.extractUserIdFromToken(authHeader.replace("Bearer ", ""));
    }

    @ModelAttribute("userType")
    public String extractUserTypeFromToken(HttpServletRequest request) throws ExtractInfoUserFromTokenException {
        String path = request.getRequestURI();

        if (PublicRoutesConfig.PUBLIC_ROUTES.contains(path)) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExtractInfoUserFromTokenException("El token es inválido o no se proporcionó.");
        }

        return jwtUtilService.extractRoleFromToken(authHeader.replace("Bearer ", ""));
    }
}

