package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.CantidadCategoriaDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelLibrosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PorcentajeCategoriaDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.RankingLibroDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.PanelLibrosService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de las estadísticas de libros utilizado para armar el PanelLibrosDTO
public class PanelLibrosMapper {

    @Autowired
    private PanelLibrosService panelLibrosService;

    /**
     * Arma el PanelLibrosDTO con las estadísticas de libros
     * @return PanelLibrosDTO con los datos del panel
     */
    public PanelLibrosDTO toDTO() {
        PanelLibrosDTO panelLibrosDTO = new PanelLibrosDTO();

        panelLibrosDTO.setLibrosRegistrados(panelLibrosService.obtenerLibrosRegistrados());
        panelLibrosDTO.setLibrosIndependientes(panelLibrosService.obtenerLibrosIndependientes());
        panelLibrosDTO.setCategoriasLibros(
            toCantidadCategoriaDTOList(panelLibrosService.obtenerCantidadLibrosPorCategoria())
        );
        panelLibrosDTO.setCategoriasMasGuardadas(
            toPorcentajeCategoriaDTOList(panelLibrosService.obtenerCategoriasMasGuardadas())
        );
        panelLibrosDTO.setLibrosMasGuardados(
            toRankingLibroDTOList(panelLibrosService.obtenerLibrosMasGuardados())
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

    /**
     * Pasa un mapa de categorías y cantidades a una lista de CantidadCategoriaDTO
     * @param cantidades El mapa con el nombre de la categoría y su cantidad
     * @return Lista de CantidadCategoriaDTO en el mismo orden del mapa
     */
    private List<CantidadCategoriaDTO> toCantidadCategoriaDTOList(Map<String, Long> cantidades) {
        return cantidades
            .entrySet()
            .stream()
            .map((entrada) -> new CantidadCategoriaDTO(entrada.getKey(), entrada.getValue()))
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