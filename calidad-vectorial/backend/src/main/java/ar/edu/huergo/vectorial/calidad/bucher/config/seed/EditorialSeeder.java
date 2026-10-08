package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.EditorialSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class EditorialSeeder implements Seeder {

    private static final String ARCHIVO = "seed/editoriales.json";

    private final SeedDataReader reader;
    private final EditorialRepository editorialRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, EditorialSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(EditorialSeed seed) {
        editorialRepository
            .findByNombreIgnoringCase(seed.nombre())
            .orElseGet(() ->
                editorialRepository.save(new Editorial(seed.nombre(), seed.urlWikipedia()))
            );
    }
}