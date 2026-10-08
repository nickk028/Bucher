package ar.edu.huergo.vectorial.calidad.bucher.config;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.Seeder;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Marca esta clase como una clase de configuración de Spring
// Clase para inicializar datos en la base de datos al iniciar la aplicación
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository rolRepository, List<Seeder> seeders) {
        return (args) -> {
            if (rolRepository.count() > 0) {
                return;
            }
            seeders.forEach(Seeder::ejecutar);
        };
    }
}