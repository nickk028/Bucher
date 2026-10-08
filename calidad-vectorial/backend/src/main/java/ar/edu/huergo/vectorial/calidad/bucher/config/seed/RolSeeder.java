package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class RolSeeder implements Seeder {

    private static final String ARCHIVO = "seed/roles.json";

    private final SeedDataReader reader;
    private final RolRepository rolRepository;

    @Override
    public void ejecutar() {
        reader
            .leer(ARCHIVO, String.class)
            .forEach((nombre) ->
                rolRepository
                    .findByNombre(nombre)
                    .orElseGet(() -> rolRepository.save(new Rol(nombre)))
            );
    }
}