package ar.edu.huergo.vectorial.calidad.bucher.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.PasswordValidator;

@Configuration // Marca esta clase como una clase de configuración de Spring
// Clase para inicializar datos en la base de datos al iniciar la aplicación
public class DataInitializer {

    @Bean // Marca este método como un bean de Spring para que Spring pueda ejecutarlo 
    CommandLineRunner initData(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            Rol admin = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("LECTOR").orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

            if (usuarioRepository.findByUsername("admin@gmail.com").isEmpty()) {
                String adminPassword = "AdminSuperSegura@123";
                PasswordValidator.validate(adminPassword);
                Usuario u = new Usuario("admin@gmail.com", encoder.encode(adminPassword));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

            if (usuarioRepository.findByUsername("lector@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Usuario u = new Usuario("lector@gmail.com", encoder.encode(clientePassword));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }
        };
    }
}