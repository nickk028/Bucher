package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
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
    
    /**
     * Pasa de PubicacionUpdateDTO a entidad Publicacion
     * @param PubicacionUpdateDTO El DTO a transformar en entidad
     * @return Pubicacion como entidad
     */
    public Publicacion toEntity(PublicacionUpdateDTO publicacionUpdateDTO) {
    Publicacion publicacion = new Publicacion();

    if (publicacionUpdateDTO.getDescripcion() == null || publicacionUpdateDTO.getDescripcion().isEmpty()) {
        publicacion.setDescripcion("nada");
    } else {
        publicacion.setDescripcion(publicacionUpdateDTO.getDescripcion());
    }

    publicacion.setLimiteDias(publicacionUpdateDTO.getLimiteDias());

    if (publicacionUpdateDTO.getDetallesEstadoLibro() == null || publicacionUpdateDTO.getDetallesEstadoLibro().isEmpty()) {
        publicacion.setDetallesEstadoLibro("nada");
    } else {
        publicacion.setDetallesEstadoLibro(publicacionUpdateDTO.getDetallesEstadoLibro());
    }

    if (publicacionUpdateDTO.getEstadoPublicacion() == null) {
        publicacion.setEstadoPublicacion(Estado.Indefinido);
    } else {
        publicacion.setEstadoPublicacion(publicacionUpdateDTO.getEstadoPublicacion());
    }

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

        publicacionResponseDTO.setId(publicacion.getId());
        publicacionResponseDTO.setTitulo(publicacion.getLibro().getTitulo());
        publicacionResponseDTO.setDescripcion(publicacion.getDescripcion());
        publicacionResponseDTO.setLimiteDias(publicacion.getLimiteDias());
        publicacionResponseDTO.setUsuarioCreador(publicacion.getUsuario().getUsername());
        publicacionResponseDTO.setFechaCreacion(publicacion.getFechaCreacion());
        publicacionResponseDTO.setEstadoPublicacion(publicacion.getEstadoPublicacion());
        publicacionResponseDTO.setNombre(publicacion.getLibro().getAutor().getNombre());
        publicacionResponseDTO.setUrlFoto(publicacion.getLibro().getUrlFoto());

        return publicacionResponseDTO;
    }

        /**
     * Pasa de Pubicacion a entidad PublicacionBasicDTO
     * @param Pubicacion a pasar a DTO
     * @return Pubicacion como BasicDTO
     */
    public PublicacionBasicDTO toBasicDTO(Publicacion publicacion) {
        if (publicacion == null) {
            return null;
        }
        PublicacionBasicDTO publicacionBasicDTO = new PublicacionBasicDTO();

        publicacionBasicDTO.setId(publicacion.getId());
        publicacionBasicDTO.setTitulo(publicacion.getLibro().getTitulo());
        publicacionBasicDTO.setUsuarioNickname(publicacion.getUsuario().getNickname());
        publicacionBasicDTO.setEstadoPublicacion(publicacion.getEstadoPublicacion());
        publicacionBasicDTO.setUrlFoto(publicacion.getLibro().getUrlFoto());
        publicacionBasicDTO.setLimiteDias(publicacion.getLimiteDias());

        return publicacionBasicDTO;
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

    /**
     * Pasa de una lista de Publicaciones a una lista de PublicacionesBasicDTO
     * @param PubicacionesBasicDTO La lista de Publicaciones a transformar en BasicDTO
     * @return PublicacionesBasicDTO como DTO
     */
    public List<PublicacionBasicDTO> toBasicDTOList(Set<Publicacion> publicaciones) {
        if (publicaciones == null) {
            return new ArrayList<>();
        }
        return publicaciones
            .stream()
            .map(this::toBasicDTO)
            .collect(Collectors.toList());
    }

    public Map<Categoria, List<PublicacionBasicDTO>> toDTOMap(Map<Categoria, Set<Publicacion>> original) {
        Map<Categoria, List<PublicacionBasicDTO>> resultado = new HashMap<>();
        for (Map.Entry<Categoria, Set<Publicacion>> entry : original.entrySet()) {
            Set<Publicacion> publicacionesDeLaCategoria = entry.getValue();
            List<PublicacionBasicDTO> dtos = toBasicDTOList(publicacionesDeLaCategoria);
            Categoria categoria = entry.getKey();
            resultado.put(categoria, dtos);
        }
        return (resultado);
    }
}