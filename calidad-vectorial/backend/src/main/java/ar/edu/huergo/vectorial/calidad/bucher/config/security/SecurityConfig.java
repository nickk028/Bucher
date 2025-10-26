package ar.edu.huergo.vectorial.calidad.bucher.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;

@Configuration // Marca esta clase como una clase de configuración de Spring
@EnableMethodSecurity // Habilita la seguridad a nivel de método con anotaciones
// Clase de configuración de seguridad de Spring Security
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        // Configuración central de Spring Security con JWT:
        // - Deshabilitamos CSRF porque no usamos cookies/sesiones en un API stateless.
        // - Forzamos manejo de sesión sin estado (los datos de auth vienen en el JWT).
        // - Permitimos libre acceso solo al login, el resto requiere autenticación y roles.
        // - Registramos nuestro filtro JWT antes del filtro de usuario/contraseña.
        http.cors(cors -> {})
        .csrf(csrf -> csrf.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Permite recursos estáticos (CSS, JS, imágenes) públicos
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/favicon.ico").permitAll()

                // Login y Registro
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuario/registrar").permitAll()

                // Usuario
                .requestMatchers(HttpMethod.GET, "/usuario").hasRole("ADMIN")

                // Publicacion
                .requestMatchers(HttpMethod.GET, "/publicacion/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.GET, "/publicacion/estado/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/publicacion/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.POST, "/publicacion/devolucion/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/publicacion/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.DELETE, "/publicacion/**").hasAnyRole("ADMIN", "LECTOR")

                // Registro
                .requestMatchers(HttpMethod.GET, "/registro").hasAnyRole("ADMIN", "LECTOR")

                // Biblioteca
                .requestMatchers(HttpMethod.GET, "/biblioteca/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.POST, "/biblioteca/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.PUT, "/biblioteca/**").hasAnyRole("ADMIN", "LECTOR")
                .requestMatchers(HttpMethod.DELETE, "/biblioteca/**").hasAnyRole("ADMIN", "LECTOR")

                // Libro
                .requestMatchers(HttpMethod.GET, "/libro/**").hasAnyRole("ADMIN", "LECTOR")

                .anyRequest().authenticated())

                .exceptionHandling(
                    exceptions -> exceptions.accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(java.util.Map.of(
                "type", "https://http.dev/problems/access-denied",
                "title", "Acceso denegado",
                "status", 403,
                "detail", "No tienes permisos para acceder a este recurso"));

            response.getWriter().write(jsonResponse);
        };
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(java.util.Map.of(
                "type", "https://http.dev/problems/unauthorized",
                "title", "No autorizado",
                "status", 401,
                "detail", "Credenciales inválidas o faltantes"));

            response.getWriter().write(jsonResponse);
        };
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        // Adaptamos nuestra entidad Usuario a UserDetails de Spring Security.
        return username -> usuarioRepository.findByUsername(username)
            .map(usuario ->
                org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername()).password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(r -> r.getNombre())
                .toArray(String[]::new))
                .build())
                .orElseThrow(
                    () -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder) {

        // Provider de autenticación que usa nuestro UserDetailsService y el encoder
        // para validar credentials en /auth/login.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        // Exponemos el AuthenticationManager que usará el controlador de login.
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // tu frontend
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true); // importante si usas cookie HTTP-only
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}