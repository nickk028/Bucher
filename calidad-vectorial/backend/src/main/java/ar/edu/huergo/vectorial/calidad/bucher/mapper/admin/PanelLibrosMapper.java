package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelLibrosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelLibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de libros utilizado para armar el PanelLibrosDTO
public class PanelLibrosMapper {

    @Autowired
    private PanelLibrosService panelLibrosService;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * Arma el PanelLibrosDTO con las estadísticas de libros
     * @return PanelLibrosDTO con los datos del panel
     */
    public PanelLibrosDTO toDTO() {
        PanelLibrosDTO panelLibrosDTO = new PanelLibrosDTO();

        panelLibrosDTO.setLibrosRegistrados(panelLibrosService.obtenerLibrosRegistrados());
        panelLibrosDTO.setLibrosIndependientes(panelLibrosService.obtenerLibrosIndependientes());
        panelLibrosDTO.setCategoriasLibros(
            adminMapper.toCantidadCategoriaDTOList(
                panelLibrosService.obtenerCantidadLibrosPorCategoria()
            )
        );
        panelLibrosDTO.setCategoriasMasGuardadas(
            adminMapper.toPorcentajeCategoriaDTOList(
                panelLibrosService.obtenerCategoriasMasGuardadas()
            )
        );
        panelLibrosDTO.setLibrosMasGuardados(
            adminMapper.toRankingLibroDTOList(panelLibrosService.obtenerLibrosMasGuardados())
        );
        panelLibrosDTO.setPorcentajeLibrosGuardados(
            panelLibrosService.obtenerPorcentajeLibrosGuardados()
        );
        panelLibrosDTO.setPorcentajeLibrosNombradosEnPosteos(
            panelLibrosService.obtenerPorcentajeLibrosNombradosEnPosteos()
        );
        panelLibrosDTO.setPorcentajeLibrosConPublicaciones(
            panelLibrosService.obtenerPorcentajeLibrosConPublicaciones()
        );

        return panelLibrosDTO;
    }
}