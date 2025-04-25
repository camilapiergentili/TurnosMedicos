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

    @PostConstruct
    public void init() {
        System.out.println("🔑 SECRET_KEY: " + SECRET_KEY);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        System.out.println("📌 Generando token para: " + user.getUsername());
        System.out.println("🔑 Clave usada para firmar: " + SECRET_KEY);

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", "ROLE_" + user.getUserType().name())
                .claim("id", user.getIdUser())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("✅ Token generado: " + token);
        return token;
    }

    public boolean validateToken(String token) throws InvalidTokenException {
        try{
            System.out.println("📩 Token recibido para validación: " + token);
            System.out.println("🔑 Clave usada en validación: " + SECRET_KEY);

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

            System.out.println("🔍 Claims completos: " + claims); // 👈 Ver todos los claims
            System.out.println("🔍 Subject (username): " + claims.getSubject());

            return claims.getSubject();
        } catch (Exception err) {
            throw new ExtractInfoUserFromTokenException("Error al extraer el email del token: " + err.getMessage());
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        System.out.println("🔍 Header Authorization recibido: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("🔍 Token extraído: " + token);
            return token;
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
