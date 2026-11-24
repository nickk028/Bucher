package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaUsuarioDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.TablaEstadisticaUsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la tabla de usuarios del panel de administración utilizada para pasar de entidad a DTO
public class TablaEstadisticaUsuarioMapper {

    @Autowired
    private TablaEstadisticaUsuarioService tablaEstadisticaUsuarioService;

    /**
     * Pasa una página de Usuario a una página de TablaEstadisticaUsuarioDTO
     * @param usuarios La página de usuarios a convertir
     * @return Página de TablaEstadisticaUsuarioDTO
     */
    public Page<TablaEstadisticaUsuarioDTO> toDTOPage(Page<Usuario> usuarios) {
        List<Long> ids = usuarios.getContent().stream().map(Usuario::getId).toList();
        Map<Long, Long> cantidadesPublicaciones =
            tablaEstadisticaUsuarioService.obtenerCantidadPublicaciones(ids);
        Map<Long, Long> cantidadesPosteos = tablaEstadisticaUsuarioService.obtenerCantidadPosteos(
            ids
        );
        Map<Long, Long> cantidadesDenuncias = tablaEstadisticaUsuarioService.obtenerCantidadDenuncias(
            ids
        );

        return usuarios.map((usuario) ->
            toDTO(usuario, cantidadesPublicaciones, cantidadesPosteos, cantidadesDenuncias)
        );
    }

    private TablaEstadisticaUsuarioDTO toDTO(
        Usuario usuario,
        Map<Long, Long> cantidadesPublicaciones,
        Map<Long, Long> cantidadesPosteos,
        Map<Long, Long> cantidadesDenuncias
    ) {
        TablaEstadisticaUsuarioDTO tablaEstadisticaUsuarioDTO = new TablaEstadisticaUsuarioDTO();

        tablaEstadisticaUsuarioDTO.setId(usuario.getId());
        tablaEstadisticaUsuarioDTO.setNickname(usuario.getNickname());
        tablaEstadisticaUsuarioDTO.setUsername(usuario.getUsername());
        tablaEstadisticaUsuarioDTO.setCantPublicaciones(
            cantidadesPublicaciones.getOrDefault(usuario.getId(), 0L)
        );
        tablaEstadisticaUsuarioDTO.setCantPosteos(
            cantidadesPosteos.getOrDefault(usuario.getId(), 0L)
        );
        tablaEstadisticaUsuarioDTO.setCantDenuncias(
            cantidadesDenuncias.getOrDefault(usuario.getId(), 0L)
        );

        return tablaEstadisticaUsuarioDTO;
    }
}