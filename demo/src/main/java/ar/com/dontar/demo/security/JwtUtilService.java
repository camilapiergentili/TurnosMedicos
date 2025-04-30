package ar.com.dontar.demo.security;

import ar.com.dontar.demo.exception.ExtractInfoUserFromTokenException;
import ar.com.dontar.demo.exception.InvalidTokenException;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtilService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", "ROLE_" + user.getUserType().name())
                .claim("id", user.getIdUser())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) throws InvalidTokenException {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            throw new InvalidTokenException("Token inválido " + e.getMessage());
        }
    }

    public String getUsernameFromToken(String token) throws ExtractInfoUserFromTokenException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception err) {
            throw new ExtractInfoUserFromTokenException("Error al extraer el email del token: " + err.getMessage());
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            return header.substring(7);
        }
        return null;
    }

    public Long extractUserIdFromToken(String token) throws ExtractInfoUserFromTokenException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", Long.class);
        } catch (Exception err) {
            throw new ExtractInfoUserFromTokenException("Error al extraer el ID del token: " + err.getMessage());
        }
    }

    public String extractRoleFromToken(String token) throws ExtractInfoUserFromTokenException {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);
        }catch (Exception err){
            throw new ExtractInfoUserFromTokenException("Error al extraer el rol del usuario del token: " + err.getMessage());
        }
    }

}
