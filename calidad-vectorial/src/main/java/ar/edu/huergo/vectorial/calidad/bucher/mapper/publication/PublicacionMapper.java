package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Publicacion utiizada para pasar de entidad a DTO y de DTO a entidad
public class PublicacionMapper {

    /**
     * Pasa de PubicacionCreateDTO a entidad Publicacion
     * @param PubicacionCreateDTO El DTO a transformar en entidad
     * @return Pubicacion como entidad
     */
    public Publicacion toEntity(PublicacionCreateDTO publicacionCreateDTO) {
        Publicacion publicacion = new Publicacion();
        publicacion.setDescripcion(publicacionCreateDTO.getDescripcion());
        publicacion.setLimiteDias(publicacionCreateDTO.getLimiteDias());

        return publicacion;
    }

    public Publicacion toEntityUpdate(PublicacionUpdateDTO publicacionUpdateDTO) {
        Publicacion publicacion = new Publicacion();
        publicacion.setDescripcion(publicacionUpdateDTO.getDescripcion());
        publicacion.setLimiteDias(publicacionUpdateDTO.getLimiteDias());
        return publicacion;
    }


    /**
     * Pasa de Pubicacion a entidad PublicacionResponseDTO
     * @param Pubicacion a pasar a DTO
     * @return Pubicacion como ResponseDTO
     */
    public PublicacionResponseDTO toDTO(Publicacion publicacion) {
        if (publicacion == null) {
            return null;
        }
        PublicacionResponseDTO publicacionResponseDTO = new PublicacionResponseDTO();

        publicacionResponseDTO.setTitulo(publicacion.getLibro().getTitulo());
        publicacionResponseDTO.setDescripcion(publicacion.getDescripcion());
        publicacionResponseDTO.setLimiteDias(publicacion.getLimiteDias());
        publicacionResponseDTO.setUsuarioCreador(publicacion.getUsuario().getUsername());
        publicacionResponseDTO.setFechaCreacion(publicacion.getFechaCreacion());
        publicacionResponseDTO.setEstadoPublicacion(publicacion.getEstadoPublicacion());

        return publicacionResponseDTO;
    }

    /**
     * Pasa de una lista de Publicaciones a una lista de PublicacionesResponseDTO
     * @param PubicacionesResponseDTO La lista de Publicaciones a transformar en ResponseDTO
     * @return PublicacionesResponseDTO como DTO
     */
    public List<PublicacionResponseDTO> toDTOList(Set<Publicacion> publicaciones) {
        if (publicaciones == null) {
            return new ArrayList<>();
        }
        return publicaciones
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}