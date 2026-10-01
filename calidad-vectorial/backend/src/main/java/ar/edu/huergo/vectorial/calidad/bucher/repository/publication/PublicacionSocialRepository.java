package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionSocialRepository extends JpaRepository<PublicacionSocial, Long> {
    List<PublicacionSocial> findAllByUsuario(Usuario usuario);
    Optional<PublicacionSocial> findByUsuarioAndLibroAndFechaCreacion(
        Usuario usuario,
        Libro libro,
        LocalDate fechaCreacion
    );
    Optional<PublicacionSocial> findByUsuarioAndFechaCreacion(
        Usuario usuario,
        LocalDate fechaCreacion
    );
}
