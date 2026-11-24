package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroSocialDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.deletion.RegistroEliminacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.EtiquetaPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.TipoVoto;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionSocialService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.VotoPublicacionSocialService;
import ar.edu.huergo.vectorial.calidad.bucher.util.TiempoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad PublicacionSocial utiizada para pasar de entidad a DTO y de DTO a entidad
public class PublicacionSocialMapper {

    @Autowired
    private PublicacionSocialService publicacionSocialService;

    @Autowired
    private VotoPublicacionSocialService votoPublicacionSocialService;

    /**
     * Pasa de PublicacionSocialCreateDTO a entidad PublicacionSocial
     * @param publicacionSocialCreateDTO El DTO a transformar en entidad
     * @return PublicacionSocial como entidad
     */
    public PublicacionSocial toEntity(PublicacionSocialCreateDTO publicacionSocialCreateDTO) {
        if (publicacionSocialCreateDTO == null) {
            return null;
        }
        PublicacionSocial publicacionSocial = new PublicacionSocial();

        publicacionSocial.setTextoPublicacion(publicacionSocialCreateDTO.getTextoPublicacion());
        publicacionSocial.setContieneSpoilers(publicacionSocialCreateDTO.isContieneSpoilers());

        return publicacionSocial;
    }

    /**
     * Pasa de PublicacionSocial a entidad PublicacionSocialResponseDTO
     * @param publicacionSocial La publicación social a pasar a DTO
     * @param username El username del usuario que consulta
     * @param esAdmin Si el usuario que consulta es admin
     * @return PublicacionSocial como ResponseDTO
     */
    public PublicacionSocialResponseDTO toDTO(
        PublicacionSocial publicacionSocial,
        String username,
        boolean esAdmin
    ) {
        if (publicacionSocial == null) {
            return null;
        }
        List<Long> ids = List.of(publicacionSocial.getId());

        return construirDTO(
            publicacionSocial,
            publicacionSocialService.obtenerCantidadRespuestas(ids),
            votoPublicacionSocialService.obtenerVotosUsuario(username, ids),
            esAdmin
        );
    }

    /**
     * Pasa de una página de PublicacionSocial a una página de PublicacionSocialResponseDTO
     * @param publicacionesSociales La página de publicaciones sociales a convertir
     * @param username El username del usuario que consulta
     * @param esAdmin Si el usuario que consulta es admin
     * @return Página de PublicacionSocialResponseDTO
     */
    public Page<PublicacionSocialResponseDTO> toDTOPage(
        Page<PublicacionSocial> publicacionesSociales,
        String username,
        boolean esAdmin
    ) {
        List<Long> ids = publicacionesSociales
            .getContent()
            .stream()
            .map(PublicacionSocial::getId)
            .toList();
        Map<Long, Long> cantidadesRespuestas = publicacionSocialService.obtenerCantidadRespuestas(
            ids
        );
        Map<Long, TipoVoto> votosUsuario = votoPublicacionSocialService.obtenerVotosUsuario(
            username,
            ids
        );

        return publicacionesSociales.map((publicacionSocial) ->
            construirDTO(publicacionSocial, cantidadesRespuestas, votosUsuario, esAdmin)
        );
    }

    private PublicacionSocialResponseDTO construirDTO(
        PublicacionSocial publicacionSocial,
        Map<Long, Long> cantidadesRespuestas,
        Map<Long, TipoVoto> votosUsuario,
        boolean esAdmin
    ) {
        PublicacionSocialResponseDTO publicacionSocialResponseDTO =
            new PublicacionSocialResponseDTO();
        boolean eliminada = publicacionSocial.getRegistroEliminacion() != null;

        publicacionSocialResponseDTO.setId(publicacionSocial.getId());
        publicacionSocialResponseDTO.setFechaCreacion(publicacionSocial.getFechaCreacion());
        publicacionSocialResponseDTO.setTiempoTranscurrido(
            TiempoUtil.formatearTiempoTranscurrido(publicacionSocial.getFechaCreacion())
        );
        publicacionSocialResponseDTO.setPublicacionReferenciada(
            toBasicDTO(publicacionSocial.getPublicacionReferenciada(), esAdmin)
        );
        publicacionSocialResponseDTO.setCantidadRespuestas(
            cantidadesRespuestas.getOrDefault(publicacionSocial.getId(), 0L)
        );
        publicacionSocialResponseDTO.setVotosPositivos(publicacionSocial.getVotosPositivos());
        publicacionSocialResponseDTO.setVotosNegativos(publicacionSocial.getVotosNegativos());
        publicacionSocialResponseDTO.setEliminada(eliminada);

        if (!eliminada || esAdmin) {
            publicacionSocialResponseDTO.setUsuario(toUsuarioBasicDTO(publicacionSocial.getUsuario()));
            publicacionSocialResponseDTO.setTextoPublicacion(publicacionSocial.getTextoPublicacion());
            publicacionSocialResponseDTO.setContieneSpoilers(publicacionSocial.isContieneSpoilers());
            publicacionSocialResponseDTO.setLibro(toLibroSocialDTO(publicacionSocial.getLibro()));
            publicacionSocialResponseDTO.setEtiquetas(toNicknamesEtiquetados(publicacionSocial));
            publicacionSocialResponseDTO.setVotoUsuario(votosUsuario.get(publicacionSocial.getId()));
        }

        if (esAdmin) {
            publicacionSocialResponseDTO.setReportes(publicacionSocial.getReportes());
            publicacionSocialResponseDTO.setRegistroEliminacion(
                toRegistroEliminacionDTO(publicacionSocial.getRegistroEliminacion())
            );
        }

        return publicacionSocialResponseDTO;
    }

    private PublicacionSocialBasicDTO toBasicDTO(PublicacionSocial publicacionSocial, boolean esAdmin) {
        if (publicacionSocial == null) {
            return null;
        }
        PublicacionSocialBasicDTO publicacionSocialBasicDTO = new PublicacionSocialBasicDTO();
        boolean eliminada = publicacionSocial.getRegistroEliminacion() != null;

        publicacionSocialBasicDTO.setId(publicacionSocial.getId());
        publicacionSocialBasicDTO.setTiempoTranscurrido(
            TiempoUtil.formatearTiempoTranscurrido(publicacionSocial.getFechaCreacion())
        );
        publicacionSocialBasicDTO.setEliminada(eliminada);

        if (!eliminada || esAdmin) {
            publicacionSocialBasicDTO.setUsuario(toUsuarioBasicDTO(publicacionSocial.getUsuario()));
            publicacionSocialBasicDTO.setTextoPublicacion(publicacionSocial.getTextoPublicacion());
            publicacionSocialBasicDTO.setContieneSpoilers(publicacionSocial.isContieneSpoilers());
        }

        return publicacionSocialBasicDTO;
    }

    private UsuarioBasicDTO toUsuarioBasicDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioBasicDTO usuarioBasicDTO = new UsuarioBasicDTO();

        usuarioBasicDTO.setId(usuario.getId());
        usuarioBasicDTO.setUsername(usuario.getUsername());
        usuarioBasicDTO.setNickname(usuario.getNickname());
        usuarioBasicDTO.setAvatar(usuario.getAvatar().getUrl());

        return usuarioBasicDTO;
    }

    private LibroSocialDTO toLibroSocialDTO(Libro libro) {
        if (libro == null) {
            return null;
        }
        LibroSocialDTO libroSocialDTO = new LibroSocialDTO();

        libroSocialDTO.setId(libro.getId());
        libroSocialDTO.setTitulo(libro.getTitulo());
        libroSocialDTO.setUrlFoto(libro.getUrlFoto());
        libroSocialDTO.setCalificacion(libro.getCalificacion());
        libroSocialDTO.setNombreAutor(libro.getAutor().getNombre());
        libroSocialDTO.setCategorias(libro.getCategoria());

        return libroSocialDTO;
    }

    private List<String> toNicknamesEtiquetados(PublicacionSocial publicacionSocial) {
        List<String> nicknames = new ArrayList<>();
        for (EtiquetaPublicacionSocial etiqueta : publicacionSocial.getEtiquetas()) {
            if (etiqueta.getUsuario() != null) {
                nicknames.add(etiqueta.getUsuario().getNickname());
            } else {
                nicknames.add(etiqueta.getNickname());
            }
        }
        return nicknames;
    }

    private RegistroEliminacionResponseDTO toRegistroEliminacionDTO(
        RegistroEliminacionPublicacionSocial registroEliminacion
    ) {
        if (registroEliminacion == null) {
            return null;
        }
        RegistroEliminacionResponseDTO registroEliminacionResponseDTO =
            new RegistroEliminacionResponseDTO();

        registroEliminacionResponseDTO.setFechaEliminacion(registroEliminacion.getFechaEliminacion());
        registroEliminacionResponseDTO.setRazon(registroEliminacion.getRazon());
        registroEliminacionResponseDTO.setUsuarioEliminador(
            toUsuarioBasicDTO(registroEliminacion.getUsuarioEliminador())
        );

        return registroEliminacionResponseDTO;
    }
}