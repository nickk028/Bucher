package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;

public interface LibroUsuarioRepository extends JpaRepository<LibroUsuario, Long>{
    List<LibroUsuario> findByBibliotecaAndEstadoLectura (Biblioteca biblioteca, EstadoLectura estadoLectura);

}   