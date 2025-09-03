package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
}