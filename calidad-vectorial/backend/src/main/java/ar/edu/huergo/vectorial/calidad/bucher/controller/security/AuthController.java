package ar.edu.huergo.vectorial.calidad.bucher.controller.security;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.GoogleLoginDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.LoginDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.GoogleTokenService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController // Marca la clase como un controlador REST
@RequestMapping("/auth") // Mapea las solicitudes a /auth
@RequiredArgsConstructor // Genera un constructor con los campos finales
// Controlador REST para la autenticación y generación de tokens JWT
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;
    private final GoogleTokenService googleTokenService;
    @Value("${COOKIE_SECURE:false}")
    private boolean cookieSecure;

    @Value("${COOKIE_SAME_SITE:Lax}")
    private String cookieSameSite;
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
    public ResponseEntity<Map<String, String>> login(
        @RequestBody @Valid LoginDTO request,
        HttpServletResponse response
    ) {
        // Permite crear la respuesta para el navegador -> en este caso se le envía una cookie

        // 0) Verifica que el usuario exista
        usuarioService.obtenerUsuarioPorNombre(request.username());

        // 1) Autenticar credenciales username/password
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        usuarioService.registrarAcceso(request.username());

        // 2) Cargar UserDetails y derivar roles/authorities
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        Set<String> roles = new HashSet<>(
            userDetails
                .getAuthorities()
                .stream()
                .map((a) -> a.getAuthority())
                .toList()
        );

        // 3) Generar token JWT
        String token = jwtTokenService.generarToken(userDetails, roles);

        // 4) Crear cookie HTTP-only para el token JWT
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite(cookieSameSite)
            .path("/")
            .maxAge(Duration.ofSeconds(3600))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 5) Redirigir a la página principal después del login exitoso
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Autentica (o registra) a un usuario mediante un idToken de Google.
     * @param request El DTO con el idToken emitido por Google
     * @param response Permite construir la respuesta HTTP para el navegador
     * @return El token JWT generado
     */
    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> loginConGoogle(
        @RequestBody @Valid GoogleLoginDTO request,
        HttpServletResponse response
    ) {
        var payload = googleTokenService.verificarToken(request.getIdToken());

        String email = payload.getEmail();
        String nombre = (String) payload.get("name");

        usuarioService.registrarUsuarioGoogle(email, nombre);
        usuarioService.registrarAcceso(email);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Set<String> roles = new HashSet<>(
            userDetails
                .getAuthorities()
                .stream()
                .map((a) -> a.getAuthority())
                .toList()
        );

        String token = jwtTokenService.generarToken(userDetails, roles);

        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite(cookieSameSite)
            .path("/")
            .maxAge(Duration.ofSeconds(3600))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Valida el token JWT presente en la cookie HTTP-only.
     * @param request El request HTTP para acceder a las cookies.
     * @return 200 si el token es válido, 401 si no.
     */
    @GetMapping("/validar-token")
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(401)
                .header("Cache-Control", "no-store")
                .body(Map.of("message", "Token inválido"));
        }
        try {
            UserDetails userDetails = usuarioService.getUserDetailsActual();
            if (userDetails != null && jwtTokenService.esTokenValido(token, userDetails)) {
                Set<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map((a) -> a.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.toSet());

                return ResponseEntity.ok()
                    .header("Cache-Control", "no-store")
                    .body(Map.of("username", userDetails.getUsername(), "roles", roles));
            }
            return ResponseEntity.status(401)
                .header("Cache-Control", "no-store")
                .body(Map.of("message", "Token inválido"));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .header("Cache-Control", "no-store")
                .body(Map.of("message", "Token inválido"));
        }
    }

    /**
     * Cierra la sesión del usuario
     * @param response Permite construir la respuesta HTTP para el navegador
     * @return 200 ok
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Eliminar la cookie JWT
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", "")
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite(cookieSameSite)
            .path("/")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logout exitoso");
    }
}