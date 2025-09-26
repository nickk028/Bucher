package ar.edu.huergo.vectorial.calidad.bucher.config.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    // Este método se ejecuta en cada solicitud HTTP que llega a la aplicación.
    // Flujo resumido del filtro:
    // 1) Lee el header Authorization y extrae el token si comienza con "Bearer ".
    // 2) Usa JwtTokenService para obtener el username del token.
    // 3) Si no hay autenticación previa en el contexto y el token es válido,
    // crea un UsernamePasswordAuthenticationToken con las autoridades del usuario
    // y lo coloca en el SecurityContext.
    // 4) Continúa la cadena de filtros para que el request llegue al controlador.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = null;

        // 1) Primero intentar obtener el token del header Authorization (para compatibilidad con API)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2) Si no hay token en el header, buscar en las cookies
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JWT_TOKEN".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        // 3) Si encontramos un token (ya sea en header o cookie), procesarlo
        if (token != null && !token.isEmpty()) {
            try {
                username = jwtTokenService.extraerUsername(token);
            } catch (Exception ignored) {
                // Token inválido, username se queda como null
            }

            // 4) Si hay un username y no hay autenticación previa, autenticar al usuario
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenService.esTokenValido(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}