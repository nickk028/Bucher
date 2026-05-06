package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long>{
    List<Biblioteca> findAllByUsuario (Usuario usuario);
}