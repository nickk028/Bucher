package ar.edu.huergo.vectorial.calidad.bucher.controller.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.huergo.vectorial.calidad.bucher.service.security.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller // Marca la clase como un controlador REST
@RequestMapping("/auth") // Mapea las solicitudes a /auth
@RequiredArgsConstructor // Genera un constructor con los campos finales
// Controlador REST para la autenticación y generación de tokens JWT
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    /**
     * Muestra la interfaz de login al usuario
     * @param model El modelo de Spring MVC para pasar datos a la vista Thymeleaf
     * @param error Indica si hubo un error en el login anterior
     * @return El nombre de la vista Thymeleaf "login" que se renderizará
     */
    @GetMapping("/login")
    public String mostrarLogin(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, inténtalo de nuevo.");
        }
        return "login";
    }

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
    public String procesarLogin(@RequestParam String username, @RequestParam String password,
        HttpServletResponse response, // Permite crear la respuesta para el navegador -> en este caso se le envía una cookie
        RedirectAttributes redirectAttributes) { // Permite pasar variables entre direcciones y se inyecta como parámetro

        try {
            // 1) Autenticar credenciales username/password
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

            // 2) Cargar UserDetails y derivar roles/authorities
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
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
            redirectAttributes.addFlashAttribute("message", "Login exitoso. Bienvenido " + username + "!");
            return "redirect:/index"; // Redirige a la url del index

        } catch (BadCredentialsException e) {
            // 6) Si las credenciales son inválidas, redirigir al login con error
            return "redirect:/auth/login?error=true";
        } catch (Exception e) {
            // 7) Cualquier otro error
            return "redirect:/auth/login?error=true";
        }
    }

    /**
     * Cierra la sesión del usuario
     * @param response Permite construir la respuesta HTTP para el navegador
     * @param redirectAttributes Para pasar atributos entre redirecciones HTTP
     * @return Redirección al login
     */
    @PostMapping("/logout")
    public String logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // Eliminar la cookie JWT
        Cookie jwtCookie = new Cookie("JWT_TOKEN", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expira inmediatamente

        response.addCookie(jwtCookie);

        // Del parámetro redirectAttributes
        redirectAttributes.addFlashAttribute("message", "Has cerrado sesión exitosamente.");
        return "redirect:/auth/login";
    }
}