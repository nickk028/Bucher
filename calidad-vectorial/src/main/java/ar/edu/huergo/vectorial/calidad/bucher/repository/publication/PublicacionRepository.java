package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findAllByUsuario(Usuario usuario);
}