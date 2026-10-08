package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.LibroSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
@RequiredArgsConstructor
public class LibroSeeder implements Seeder {

    private static final String PATRON_ARCHIVOS = "seed/libros/*.json";

    private final SeedDataReader reader;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final EditorialRepository editorialRepository;
    private final PrecioHistoricoService precioHistoricoService;

    @Override
    public void ejecutar() {
        reader.leerTodos(PATRON_ARCHIVOS, LibroSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(LibroSeed seed) {
        if (
            libroRepository
                .findByTituloIgnoreCaseAndEdicion(seed.titulo(), seed.edicion())
                .isPresent()
        ) {
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(seed.titulo());
        libro.setDescripcion(seed.descripcion());
        libro.setPaginas(seed.paginas());
        libro.setEdicion(seed.edicion());
        libro.setCalificacion(seed.calificacion());
        libro.setFechaPublicacion(seed.fechaPublicacion());
        libro.setUrlFoto(seed.urlFoto());
        libro.setPrecio(seed.precio());
        libro.setCategoria(new HashSet<>(seed.categorias()));
        libro.setEditorial(buscarEditorial(seed.editorial()));
        libro.setAutor(buscarAutor(seed.autor()));

        Libro libroGuardado = libroRepository.save(libro);
        precioHistoricoService.crearPrecioHistorico(libroGuardado, libroGuardado.getPrecio());
    }

    private Autor buscarAutor(String nombre) {
        return autorRepository
            .findByNombreIgnoringCase(nombre)
            .orElseThrow(() -> new IllegalStateException("Autor de seed inexistente: " + nombre));
    }

    private Editorial buscarEditorial(String nombre) {
        return editorialRepository
            .findByNombreIgnoringCase(nombre)
            .orElseThrow(() ->
                new IllegalStateException("Editorial de seed inexistente: " + nombre)
            );
    }
}