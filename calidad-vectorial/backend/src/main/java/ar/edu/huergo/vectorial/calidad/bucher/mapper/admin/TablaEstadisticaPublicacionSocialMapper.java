package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaPublicacionSocialDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la tabla de publicaciones sociales del panel de administración utilizada para pasar de entidad a DTO
public class TablaEstadisticaPublicacionSocialMapper {

    /**
     * Pasa una página de PublicacionSocial a una página de TablaEstadisticaPublicacionSocialDTO
     * @param publicacionesSociales La página de publicaciones sociales a convertir
     * @return Página de TablaEstadisticaPublicacionSocialDTO
     */
    public Page<TablaEstadisticaPublicacionSocialDTO> toDTOPage(
        Page<PublicacionSocial> publicacionesSociales
    ) {
        return publicacionesSociales.map(this::toDTO);
    }

    private TablaEstadisticaPublicacionSocialDTO toDTO(PublicacionSocial publicacionSocial) {
        TablaEstadisticaPublicacionSocialDTO tablaEstadisticaPublicacionSocialDTO =
            new TablaEstadisticaPublicacionSocialDTO();

        tablaEstadisticaPublicacionSocialDTO.setId(publicacionSocial.getId());
        tablaEstadisticaPublicacionSocialDTO.setNickname(publicacionSocial.getUsuario().getNickname());
        tablaEstadisticaPublicacionSocialDTO.setTextoPublicacion(
            publicacionSocial.getTextoPublicacion()
        );
        tablaEstadisticaPublicacionSocialDTO.setCantDenuncias(publicacionSocial.getReportes());
        tablaEstadisticaPublicacionSocialDTO.setFechaCreacion(publicacionSocial.getFechaCreacion());
        tablaEstadisticaPublicacionSocialDTO.setEliminada(
            publicacionSocial.getRegistroEliminacion() != null
        );
        tablaEstadisticaPublicacionSocialDTO.setRespuesta(
            publicacionSocial.getPublicacionReferenciada() != null
        );

        return tablaEstadisticaPublicacionSocialDTO;
    }
}
