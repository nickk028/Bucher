package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.ReportePublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.ReportePublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de ReportePublicacionSocial
@Service
public class ReportePublicacionSocialService {

    @Autowired
    private ReportePublicacionSocialRepository reportePublicacionSocialRepository;

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private PublicacionSocialService publicacionSocialService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra el reporte de un usuario sobre una publicación.
     * El contador se actualiza de forma atómica en la base de datos.
     * @param publicacionId El ID de la publicación a reportar
     * @param username El username del usuario que reporta
     * @throws EntityNotFoundException Si no se encuentra el usuario o la publicación
     * @throws IllegalArgumentException Si la publicación está eliminada, es propia o ya fue reportada por el usuario
     */
    @Transactional
    public void reportarPublicacion(Long publicacionId, String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        PublicacionSocial publicacion = publicacionSocialService.obtenerPublicacionSocialPorId(
            publicacionId
        );

        if (publicacion.getRegistroEliminacion() != null) {
            throw new IllegalArgumentException("No se puede reportar una publicación eliminada");
        }
        if (publicacion.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("No se puede reportar una publicación propia");
        }
        if (
            reportePublicacionSocialRepository.existsByUsuarioIdAndPublicacionSocialId(
                usuario.getId(),
                publicacionId
            )
        ) {
            throw new IllegalArgumentException("El usuario ya reportó esta publicación");
        }

        ReportePublicacionSocial reporte = new ReportePublicacionSocial();
        reporte.setUsuario(usuario);
        reporte.setPublicacionSocial(publicacion);
        try {
            reportePublicacionSocialRepository.saveAndFlush(reporte);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El usuario ya reportó esta publicación");
        }

        publicacionSocialRepository.sumarReportes(publicacionId, 1);
    }
}