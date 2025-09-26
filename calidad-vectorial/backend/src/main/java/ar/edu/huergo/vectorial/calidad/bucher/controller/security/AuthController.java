package ar.edu.huergo.vectorial.calidad.bucher.controller.security;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.LoginDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;

@RestController // Marca la clase como un controlador REST
@RequestMapping("/auth") // Mapea las solicitudes a /auth
@RequiredArgsConstructor // Genera un constructor con los campos finales
// Controlador REST para la autenticación y generación de tokens JWT
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;


    /**
     * Procesa el form de login y autentica al usuario
     * @param username El nombre de usuario enviado desde el HTML
     * @param password La contraseña enviada desde el HTML
     * @param response Permite construir la respuesta HTTP para el navegador
     * @param redirectAttributes Para pasar atributos entre redirecciones HTTP
     * @return Redirección al index
     * @throws BadCredentialsException Si las credenciales son inválidas
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO request,
        HttpServletResponse response) {  // Permite crear la respuesta para el navegador -> en este caso se le envía una cookie


            
        // 1) Autenticar credenciales username/password
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        // 2) Cargar UserDetails y derivar roles/authorities
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        Set<String> roles = new HashSet<>(
            userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList());

        // 3) Generar token JWT
        String token = jwtTokenService.generarToken(userDetails, roles);

        // 4) Crear cookie HTTP-only para el token JWT
        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setHttpOnly(true); // Previene acceso desde JavaScript (XSS)
        jwtCookie.setSecure(false); // Para http y no https
        jwtCookie.setPath("/"); // La cookie estará disponible en todo el sitio
        jwtCookie.setMaxAge(3600); // Duración de la cookie de 3600 segundos (lo que dura el token)

        response.addCookie(jwtCookie);

        // 5) Redirigir a la página principal después del login exitoso
        return ResponseEntity.ok(Map.of("token", token));
    }
}