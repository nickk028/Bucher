package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de Publicacion
@Service
public class PublicacionSocialService {

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todas las publicaciones sociales
     * @return Un conjunto de todas las publicaciones sociales
     */
    public Set<PublicacionSocial> obtenerTodasLasPublicacionesSociales() {
        return new HashSet<>(publicacionSocialRepository.findAll());
    }

    public long obtenerCantidadPublicacionesSocialesPorUsuario(String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        return publicacionSocialRepository.countByUsuarioId(usuario.getId());
    }
}
