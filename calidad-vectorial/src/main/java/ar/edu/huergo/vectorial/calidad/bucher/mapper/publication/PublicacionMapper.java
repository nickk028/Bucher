package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;

@Component
public class PublicacionMapper {

    public Publicacion toEntity(PublicacionCreateDTO publicacionCreateDTO) {
        Publicacion publicacion = new Publicacion();
        publicacion.setDescripcion(publicacionCreateDTO.getDescripcion());
        publicacion.setLimiteDias(publicacionCreateDTO.getLimiteDias());

        return publicacion;
    }

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