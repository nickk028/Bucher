package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Autor
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoringCase(String nombre);
}
