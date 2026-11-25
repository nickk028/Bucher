package ar.edu.huergo.vectorial.calidad.bucher.config;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.Seeder;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.SuscripcionRepository;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            SuscripcionRepository suscripcionRepository,
            List<Seeder> seeders
    ) {
        return args -> {
            if (suscripcionRepository.count() > 0) {
                return;
            }

            seeders.forEach(Seeder::ejecutar);
        };
    }
}