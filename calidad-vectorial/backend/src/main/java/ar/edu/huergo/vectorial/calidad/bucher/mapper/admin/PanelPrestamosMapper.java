package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPrestamosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelPrestamosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de préstamos utilizado para armar el PanelPrestamosDTO
public class PanelPrestamosMapper {

    @Autowired
    private PanelPrestamosService panelPrestamosService;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * Arma el PanelPrestamosDTO con las estadísticas de préstamos
     * @return PanelPrestamosDTO con los datos del panel
     */
    public PanelPrestamosDTO toDTO() {
        PanelPrestamosDTO panelPrestamosDTO = new PanelPrestamosDTO();

        panelPrestamosDTO.setPrestamosTotales(panelPrestamosService.obtenerPrestamosTotales());
        panelPrestamosDTO.setPrestamosActivos(panelPrestamosService.obtenerPrestamosActivos());
        panelPrestamosDTO.setPrestamosIncompletos(
            panelPrestamosService.obtenerPrestamosIncompletos()
        );
        panelPrestamosDTO.setPrestamosDemorados(
            panelPrestamosService.obtenerPrestamosDemorados()
        );
        panelPrestamosDTO.setPorcentajePrestamosActivos(
            panelPrestamosService.obtenerPorcentajePrestamosActivos()
        );
        panelPrestamosDTO.setPrestamosCompletadosPorMes(
            adminMapper.toCantidadMesDTOList(
                panelPrestamosService.obtenerCantidadPrestamosCompletadosPorMes()
            )
        );
        panelPrestamosDTO.setCategoriasPrestamos(
            adminMapper.toCantidadCategoriaDTOList(
                panelPrestamosService.obtenerCantidadPrestamosPorCategoria()
            )
        );

        return panelPrestamosDTO;
    }
}