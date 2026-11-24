package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPosteosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelPosteosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de posteos utilizado para armar el PanelPosteosDTO
public class PanelPosteosMapper {

    @Autowired
    private PanelPosteosService panelPosteosService;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * Arma el PanelPosteosDTO con las estadísticas de posteos
     * @return PanelPosteosDTO con los datos del panel
     */
    public PanelPosteosDTO toDTO() {
        PanelPosteosDTO panelPosteosDTO = new PanelPosteosDTO();

        panelPosteosDTO.setPosteosPublicados(panelPosteosService.obtenerPosteosPublicados());
        panelPosteosDTO.setPosteosConReferencias(
            panelPosteosService.obtenerPosteosConReferencias()
        );
        panelPosteosDTO.setPosteosDenunciados(panelPosteosService.obtenerPosteosDenunciados());
        panelPosteosDTO.setPorcentajePosteosDenunciados(
            panelPosteosService.obtenerPorcentajePosteosDenunciados()
        );
        panelPosteosDTO.setPosteosEliminados(panelPosteosService.obtenerPosteosEliminados());
        panelPosteosDTO.setPorcentajePosteosEliminados(
            panelPosteosService.obtenerPorcentajePosteosEliminados()
        );
        panelPosteosDTO.setPorcentajePosteosConMenciones(
            panelPosteosService.obtenerPorcentajePosteosConMenciones()
        );
        panelPosteosDTO.setPosteosPorMes(
            adminMapper.toCantidadMesDTOList(panelPosteosService.obtenerCantidadPosteosPorMes())
        );
        panelPosteosDTO.setCategoriasMasMencionadas(
            adminMapper.toPorcentajeCategoriaDTOList(
                panelPosteosService.obtenerCategoriasMasMencionadas()
            )
        );
        panelPosteosDTO.setLibrosMasMencionados(
            adminMapper.toRankingLibroDTOList(panelPosteosService.obtenerLibrosMasMencionados())
        );

        return panelPosteosDTO;
    }
}