package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;

// Repositorio JPA para la entidad Editorial
public interface EditorialRepository extends JpaRepository<Editorial, Long>{
    Optional<Editorial> findByNombre(String nombre);
}