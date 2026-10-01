package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Editorial
public interface EditorialRepository extends JpaRepository<Editorial, Long> {
    Optional<Editorial> findByNombreIgnoringCase(String nombre);
}
