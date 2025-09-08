package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;

// Repositorio JPA para la entidad Autor
public interface AutorRepository extends JpaRepository<Autor, Long>{
    Optional<Autor> findByNombre(String nombre);
}