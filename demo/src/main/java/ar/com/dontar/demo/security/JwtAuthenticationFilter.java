package ar.com.dontar.demo.security;

import ar.com.dontar.demo.configuration.PublicRoutesConfig;
import ar.com.dontar.demo.persistence.entity.UserEntity;
import ar.com.dontar.demo.service.implementation.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isPublicRoute(path)) {
            filterChain.doFilter(request, response);  // Si es pública, no validamos el token
            return;
        }

        System.out.println("🔍 Filtro JWT ejecutado para: " + request.getRequestURI());
        String token = jwtUtil.getTokenFromRequest(request);

        System.out.println("🔍 Token JWT ejecutado para: " + token);

        if(token != null) {
            try {
                if (!jwtUtil.validateToken(token)) {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                    return;
                }

                String username = jwtUtil.getUsernameFromToken(token);
                System.out.println("🔍 Usuario extraído del token: " + username);
                UserEntity userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }


            } catch (UsernameNotFoundException err){
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            } catch (ExpiredJwtException err) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "La sesión ha expirado");
                return;
            }catch (Exception e) {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la autenticación");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicRoute(String path) {
        // Verifica si la ruta está en las rutas públicas
        for (String publicRoute : PublicRoutesConfig.PUBLIC_ROUTES) {
            if (path.startsWith(publicRoute)) {
                return true;
            }
        }
        return false;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
