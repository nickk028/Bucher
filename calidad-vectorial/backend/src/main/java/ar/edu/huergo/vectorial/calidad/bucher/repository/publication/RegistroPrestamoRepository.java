package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroPrestamoRepository extends JpaRepository<RegistroPrestamo, Long> {
    List<RegistroPrestamo> findAllByUsuario(Usuario usuario);
    Optional<RegistroPrestamo> findByPublicacionAndFechaDevolucionIsNull(Publicacion publicacion);
    List<RegistroPrestamo> findByFechaPrestamoBetween(
        LocalDate fechaInicioSemana,
        LocalDate fechaActual
    );
    Optional<RegistroPrestamo> findByPublicacionAndUsuario(
        Publicacion publicacion,
        Usuario usuario
    );
}
