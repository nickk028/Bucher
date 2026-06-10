package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad PublicacionSocial utiizada para pasar de entidad a DTO y de DTO a entidad
public class PublicacionSocialMapper {
    public PublicacionSocialResponseDTO toResponseDTO(PublicacionSocial publicacionSocial) {
        PublicacionSocialResponseDTO responseDTO = new PublicacionSocialResponseDTO();
        responseDTO.setId(publicacionSocial.getId());
        responseDTO.setDescripcion(publicacionSocial.getDescripcion());
        responseDTO.setTextoPublicacion(publicacionSocial.getTextoPublicacion());
        responseDTO.setFechaCreacion(publicacionSocial.getFechaCreacion());
        responseDTO.setUsuario(publicacionSocial.getUsuario());
        return responseDTO;
    }

    public List<PublicacionSocialResponseDTO> toResponseDTOList(Set<PublicacionSocial> publicacionesSociales) {
        return publicacionesSociales.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
