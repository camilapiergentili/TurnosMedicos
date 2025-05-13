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
        System.out.println("Evaluando ruta: " + path);

        if (isPublicRoute(path)) {
            return null;
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

        if (isPublicRoute(path)) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExtractInfoUserFromTokenException("El token es inválido o no se proporcionó.");
        }

        return jwtUtilService.extractRoleFromToken(authHeader.replace("Bearer ", ""));
    }

    private boolean isPublicRoute(String path) {
        // Evita errores con o sin barra al final
        String normalizedPath = path.replaceAll("/$", "");
        return PublicRoutesConfig.PUBLIC_ROUTES.contains(normalizedPath);
    }
}

