package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.TipoVoto;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.VotoPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.VotoPublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de VotoPublicacionSocial
@Service
public class VotoPublicacionSocialService {

    @Autowired
    private VotoPublicacionSocialRepository votoPublicacionSocialRepository;

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private PublicacionSocialService publicacionSocialService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra el voto de un usuario en una publicación, o cambia el voto si ya había votado.
     * Los contadores se actualizan de forma atómica en la base de datos.
     * @param publicacionId El ID de la publicación a votar
     * @param tipo El tipo de voto
     * @param username El username del usuario que vota
     * @throws EntityNotFoundException Si no se encuentra el usuario o la publicación
     * @throws IllegalArgumentException Si la publicación está eliminada o el voto ya fue registrado
     */
    @Transactional
    public void votarPublicacion(Long publicacionId, TipoVoto tipo, String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        PublicacionSocial publicacion = publicacionSocialService.obtenerPublicacionSocialPorId(
            publicacionId
        );
        if (publicacion.getRegistroEliminacion() != null) {
            throw new IllegalArgumentException("No se puede votar una publicación eliminada");
        }

        Optional<VotoPublicacionSocial> votoExistente =
            votoPublicacionSocialRepository.findByUsuarioIdAndPublicacionSocialId(
                usuario.getId(),
                publicacionId
            );

        if (votoExistente.isEmpty()) {
            VotoPublicacionSocial votoNuevo = new VotoPublicacionSocial();
            votoNuevo.setUsuario(usuario);
            votoNuevo.setPublicacionSocial(publicacion);
            votoNuevo.setTipo(tipo);
            guardarVotoNuevo(votoNuevo);
            ajustarContador(publicacionId, tipo, 1);
            return;
        }

        VotoPublicacionSocial voto = votoExistente.get();
        TipoVoto tipoAnterior = voto.getTipo();
        if (tipoAnterior == tipo) {
            return;
        }

        voto.setTipo(tipo);
        votoPublicacionSocialRepository.saveAndFlush(voto);
        ajustarContador(publicacionId, tipoAnterior, -1);
        ajustarContador(publicacionId, tipo, 1);
    }

    /**
     * Quita el voto de un usuario en una publicación
     * @param publicacionId El ID de la publicación
     * @param username El username del usuario que quita su voto
     * @throws EntityNotFoundException Si no se encuentra el usuario o el voto
     */
    @Transactional
    public void quitarVoto(Long publicacionId, String username) throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        VotoPublicacionSocial voto = votoPublicacionSocialRepository
            .findByUsuarioIdAndPublicacionSocialId(usuario.getId(), publicacionId)
            .orElseThrow(() -> new EntityNotFoundException("Voto no encontrado"));

        TipoVoto tipo = voto.getTipo();
        votoPublicacionSocialRepository.delete(voto);
        votoPublicacionSocialRepository.flush();
        ajustarContador(publicacionId, tipo, -1);
    }

    /**
     * Obtiene los votos de un usuario sobre un conjunto de publicaciones
     * @param username El username del usuario
     * @param publicacionesIds Los IDs de las publicaciones
     * @return Mapa con el ID de la publicación y el voto del usuario (las que no votó no figuran)
     * @throws EntityNotFoundException Si no se encuentra el usuario
     */
    public Map<Long, TipoVoto> obtenerVotosUsuario(
        String username,
        Collection<Long> publicacionesIds
    ) throws EntityNotFoundException {
        if (publicacionesIds.isEmpty()) {
            return new HashMap<>();
        }
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        return votoPublicacionSocialRepository
            .findAllByUsuarioIdAndPublicacionSocialIdIn(usuario.getId(), publicacionesIds)
            .stream()
            .collect(
                Collectors.toMap(
                    (voto) -> voto.getPublicacionSocial().getId(),
                    VotoPublicacionSocial::getTipo
                )
            );
    }

    /**
     * Guarda un voto nuevo y traduce la violación de la restricción única (votos simultáneos)
     * @param voto El voto a guardar
     * @throws IllegalArgumentException Si el usuario ya votó la publicación
     */
    private void guardarVotoNuevo(VotoPublicacionSocial voto) {
        try {
            votoPublicacionSocialRepository.saveAndFlush(voto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El usuario ya votó esta publicación");
        }
    }

    /**
     * Suma o resta de forma atómica un voto al contador que corresponde al tipo
     * @param publicacionId El ID de la publicación
     * @param tipo El tipo de voto
     * @param cantidad La cantidad a sumar (negativa para restar)
     */
    private void ajustarContador(Long publicacionId, TipoVoto tipo, int cantidad) {
        if (tipo == TipoVoto.POSITIVO) {
            publicacionSocialRepository.sumarVotosPositivos(publicacionId, cantidad);
        } else {
            publicacionSocialRepository.sumarVotosNegativos(publicacionId, cantidad);
        }
    }
}