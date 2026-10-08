package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroUsuarioRepository extends JpaRepository<LibroUsuario, Long> {
    List<LibroUsuario> findByUsuarioIdAndEstadoLectura(
        Long usuarioId,
        EstadoLectura estadoLectura
    );

    boolean existsByUsuarioIdAndLibro(Long usuarioId, Libro libro);
    Optional<LibroUsuario> findByUsuarioIdAndLibro(Long usuarioId, Libro libro);
}