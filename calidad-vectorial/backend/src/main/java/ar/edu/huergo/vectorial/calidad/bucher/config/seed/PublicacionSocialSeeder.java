package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.PublicacionSocialSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
@RequiredArgsConstructor
public class PublicacionSocialSeeder implements Seeder {

    private static final String ARCHIVO = "seed/publicaciones-sociales.json";

    private final SeedDataReader reader;
    private final SeedReferences references;
    private final PublicacionSocialRepository publicacionSocialRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, PublicacionSocialSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(PublicacionSocialSeed seed) {
        Usuario usuario = references.usuario(seed.usuario());

        boolean yaExiste = publicacionSocialRepository
            .findAllByUsuario(usuario)
            .stream()
            .anyMatch((publicacion) ->
                publicacion.getTextoPublicacion().equals(seed.textoPublicacion())
            );
        if (yaExiste) {
            return;
        }

        PublicacionSocial publicacion = new PublicacionSocial();
        publicacion.setUsuario(usuario);
        publicacion.setFechaCreacion(LocalDate.now());
        publicacion.setDescripcion(seed.descripcion());
        publicacion.setTextoPublicacion(seed.textoPublicacion());
        if (seed.libro() != null) {
            publicacion.setLibro(references.libro(seed.libro()));
        }
        publicacionSocialRepository.save(publicacion);
    }
}