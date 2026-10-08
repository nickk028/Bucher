package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.RegistroPrestamoSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(7)
@RequiredArgsConstructor
public class RegistroPrestamoSeeder implements Seeder {

    private static final String ARCHIVO = "seed/prestamos.json";

    private final SeedDataReader reader;
    private final SeedReferences references;
    private final PublicacionRepository publicacionRepository;
    private final RegistroPrestamoRepository registroPrestamoRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, RegistroPrestamoSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(RegistroPrestamoSeed seed) {
        Usuario solicitante = references.usuario(seed.solicitante());
        Publicacion publicacion = buscarPublicacion(seed.publicador(), seed.libro());

        if (
            registroPrestamoRepository
                .findByPublicacionAndUsuario(publicacion, solicitante)
                .isPresent()
        ) {
            return;
        }

        RegistroPrestamo prestamo = new RegistroPrestamo();
        prestamo.setPublicacion(publicacion);
        prestamo.setUsuario(solicitante);
        prestamo.setFechaPrestamo(LocalDate.now().minusDays(seed.diasDesdePrestamo()));
        if (seed.diasDesdeDevolucion() != null) {
            prestamo.setFechaDevolucion(LocalDate.now().minusDays(seed.diasDesdeDevolucion()));
        }
        registroPrestamoRepository.save(prestamo);
    }

    private Publicacion buscarPublicacion(String publicador, String titulo) {
        return publicacionRepository
            .findAllByUsuario(references.usuario(publicador))
            .stream()
            .filter((publicacion) -> publicacion.getLibro().getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElseThrow(() ->
                new IllegalStateException(
                    "Publicación de seed inexistente: " + titulo + " de " + publicador
                )
            );
    }
}