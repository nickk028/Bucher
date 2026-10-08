package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.AutorSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
public class AutorSeeder implements Seeder {

    private static final String ARCHIVO = "seed/autores.json";

    private final SeedDataReader reader;
    private final AutorRepository autorRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, AutorSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(AutorSeed seed) {
        autorRepository
            .findByNombreIgnoringCase(seed.nombre())
            .orElseGet(() ->
                autorRepository.save(
                    new Autor(
                        seed.nombre(),
                        seed.descripcion(),
                        seed.urlWikipedia(),
                        seed.urlFotoAutor()
                    )
                )
            );
    }
}