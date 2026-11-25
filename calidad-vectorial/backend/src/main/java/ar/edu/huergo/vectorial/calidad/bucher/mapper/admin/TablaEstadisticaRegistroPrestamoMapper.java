package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaRegistroPrestamoDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la tabla de préstamos activos del panel de administración utilizada para pasar de entidad a DTO
public class TablaEstadisticaRegistroPrestamoMapper {

    /**
     * Pasa una página de RegistroPrestamo a una página de TablaEstadisticaRegistroPrestamoDTO
     * @param registrosPrestamo La página de registros de préstamo a convertir
     * @return Página de TablaEstadisticaRegistroPrestamoDTO
     */
    public Page<TablaEstadisticaRegistroPrestamoDTO> toDTOPage(
        Page<RegistroPrestamo> registrosPrestamo
    ) {
        return registrosPrestamo.map(this::toDTO);
    }

    private TablaEstadisticaRegistroPrestamoDTO toDTO(RegistroPrestamo registroPrestamo) {
        TablaEstadisticaRegistroPrestamoDTO tablaEstadisticaRegistroPrestamoDTO =
            new TablaEstadisticaRegistroPrestamoDTO();

        tablaEstadisticaRegistroPrestamoDTO.setId(registroPrestamo.getId());
        tablaEstadisticaRegistroPrestamoDTO.setNickname(
            registroPrestamo.getPublicacion().getUsuario().getNickname()
        );
        tablaEstadisticaRegistroPrestamoDTO.setTituloLibro(
            registroPrestamo.getPublicacion().getLibro().getTitulo()
        );
        tablaEstadisticaRegistroPrestamoDTO.setEstado(
            registroPrestamo.getPublicacion().getEstadoPublicacion().getNombre()
        );
        tablaEstadisticaRegistroPrestamoDTO.setFechaEntrega(registroPrestamo.getFechaPrestamo());
        tablaEstadisticaRegistroPrestamoDTO.setFechaDevolucion(registroPrestamo.getFechaDevolucion());

        return tablaEstadisticaRegistroPrestamoDTO;
    }
}