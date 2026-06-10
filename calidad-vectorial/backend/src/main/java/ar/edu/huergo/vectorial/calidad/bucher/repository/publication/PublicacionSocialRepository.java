package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface PublicacionSocialRepository extends JpaRepository<PublicacionSocial, Long> { 
    List<PublicacionSocial> findAllByUsuario(Usuario usuario);
}
