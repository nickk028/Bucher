package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long>{
    
}