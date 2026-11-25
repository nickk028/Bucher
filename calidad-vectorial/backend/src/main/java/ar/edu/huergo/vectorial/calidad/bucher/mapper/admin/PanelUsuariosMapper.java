package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelUsuariosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de usuarios utilizado para armar el PanelUsuariosDTO
public class PanelUsuariosMapper {

    @Autowired
    private PanelUsuariosService panelUsuariosService;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * Arma el PanelUsuariosDTO con las estadísticas de usuarios
     * @return PanelUsuariosDTO con los datos del panel
     */
    public PanelUsuariosDTO toDTO() {
        PanelUsuariosDTO panelUsuariosDTO = new PanelUsuariosDTO();

        panelUsuariosDTO.setUsuariosTotales(panelUsuariosService.obtenerUsuariosTotales());
        panelUsuariosDTO.setUsuariosNuevos(panelUsuariosService.obtenerUsuariosNuevos());
        panelUsuariosDTO.setUsuariosNuevosPorMes(
            adminMapper.toCantidadMesDTOList(
                panelUsuariosService.obtenerCantidadUsuariosNuevosPorMes()
            )
        );
        panelUsuariosDTO.setPorcentajeUsuariosActivos(
            panelUsuariosService.obtenerPorcentajeUsuariosActivos()
        );
        panelUsuariosDTO.setPorcentajeUsuariosInactivos(
            panelUsuariosService.obtenerPorcentajeUsuariosInactivos()
        );
        panelUsuariosDTO.setPorcentajeUsuariosConPrestamosActivos(
            panelUsuariosService.obtenerPorcentajeUsuariosConPrestamosActivos()
        );
        panelUsuariosDTO.setPorcentajeUsuariosConPublicaciones(
            panelUsuariosService.obtenerPorcentajeUsuariosConPublicaciones()
        );
        panelUsuariosDTO.setPorcentajeUsuariosConPosteos(
            panelUsuariosService.obtenerPorcentajeUsuariosConPosteos()
        );

        return panelUsuariosDTO;
    }
}