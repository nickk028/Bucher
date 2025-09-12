package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;

// Repositorio JPA para la entidad Libro
public interface LibroRepository extends JpaRepository<Libro, Long>{
    Optional<Libro> findByTituloIgnoringCase(String titulo);
    Optional<Libro> findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(String titulo, String edicion, Autor autor, Editorial editorial);
}