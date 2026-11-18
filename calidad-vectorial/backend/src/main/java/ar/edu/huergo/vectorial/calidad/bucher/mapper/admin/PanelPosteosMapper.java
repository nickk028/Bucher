package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.CantidadMesDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPosteosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PorcentajeCategoriaDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.RankingLibroDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelPosteosService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de posteos utilizado para armar el PanelPosteosDTO
public class PanelPosteosMapper {

    @Autowired
    private PanelPosteosService panelPosteosService;

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
            toCantidadMesDTOList(panelPosteosService.obtenerCantidadPosteosPorMes())
        );
        panelPosteosDTO.setCategoriasMasMencionadas(
            toPorcentajeCategoriaDTOList(panelPosteosService.obtenerCategoriasMasMencionadas())
        );
        panelPosteosDTO.setLibrosMasMencionados(
            toRankingLibroDTOList(panelPosteosService.obtenerLibrosMasMencionados())
        );

        return panelPosteosDTO;
    }

    /**
     * Pasa un mapa de meses y cantidades a una lista de CantidadMesDTO
     * @param cantidades El mapa con el nombre del mes y su cantidad de posteos
     * @return Lista de CantidadMesDTO en el mismo orden del mapa
     */
    private List<CantidadMesDTO> toCantidadMesDTOList(Map<String, Long> cantidades) {
        return cantidades
            .entrySet()
            .stream()
            .map((entrada) -> new CantidadMesDTO(entrada.getKey(), entrada.getValue()))
            .toList();
    }

    /**
     * Pasa un mapa de categorías y porcentajes a una lista de PorcentajeCategoriaDTO
     * @param porcentajes El mapa con el nombre de la categoría y su porcentaje
     * @return Lista de PorcentajeCategoriaDTO en el mismo orden del mapa
     */
    private List<PorcentajeCategoriaDTO> toPorcentajeCategoriaDTOList(
        Map<String, Double> porcentajes
    ) {
        return porcentajes
            .entrySet()
            .stream()
            .map((entrada) -> new PorcentajeCategoriaDTO(entrada.getKey(), entrada.getValue()))
            .toList();
    }

    /**
     * Pasa un mapa de libros y cantidades a una lista de RankingLibroDTO
     * @param ranking El mapa con el libro y su cantidad
     * @return Lista de RankingLibroDTO en el mismo orden del mapa
     */
    private List<RankingLibroDTO> toRankingLibroDTOList(Map<Libro, Long> ranking) {
        return ranking
            .entrySet()
            .stream()
            .map((entrada) ->
                new RankingLibroDTO(
                    entrada.getKey().getId(),
                    entrada.getKey().getTitulo(),
                    entrada.getValue()
                )
            )
            .toList();
    }
}