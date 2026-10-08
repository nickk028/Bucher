package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.PublicacionSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(6)
@RequiredArgsConstructor
public class PublicacionSeeder implements Seeder {

    private static final String ARCHIVO = "seed/publicaciones.json";
    private static final String DETALLES_ESTADO_LIBRO = "Nada";

    private final SeedDataReader reader;
    private final SeedReferences references;
    private final PublicacionRepository publicacionRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, PublicacionSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(PublicacionSeed seed) {
        Usuario usuario = references.usuario(seed.usuario());
        Libro libro = references.libro(seed.libro());

        if (
            publicacionRepository
                .findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now())
                .isPresent()
        ) {
            return;
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuario);
        publicacion.setLibro(libro);
        publicacion.setFechaCreacion(LocalDate.now());
        publicacion.setDescripcion(seed.descripcion());
        publicacion.setLimiteDias(seed.limiteDias());
        publicacion.setDetallesEstadoLibro(DETALLES_ESTADO_LIBRO);
        publicacion.setEstadoPublicacion(
            seed.estadoPublicacion() != null ? seed.estadoPublicacion() : Estado.Disponible
        );
        publicacionRepository.save(publicacion);
    }
}