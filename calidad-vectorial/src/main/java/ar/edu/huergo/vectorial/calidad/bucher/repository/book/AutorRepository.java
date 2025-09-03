package ar.edu.huergo.vectorial.calidad.bucher.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
}