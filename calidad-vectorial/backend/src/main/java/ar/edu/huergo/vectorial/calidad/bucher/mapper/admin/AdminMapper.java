package ar.edu.huergo.vectorial.calidad.bucher.mapper.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.CantidadCategoriaDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.CantidadMesDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PorcentajeCategoriaDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.RankingLibroDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper con las conversiones compartidas por los mappers de las estadísticas del panel de administración
public class AdminMapper {

    /**
     * Pasa un mapa de meses y cantidades a una lista de CantidadMesDTO
     * @param cantidades El mapa con el nombre del mes y su cantidad
     * @return Lista de CantidadMesDTO en el mismo orden del mapa
     */
    public List<CantidadMesDTO> toCantidadMesDTOList(Map<String, Long> cantidades) {
        return cantidades
            .entrySet()
            .stream()
            .map((entrada) -> new CantidadMesDTO(entrada.getKey(), entrada.getValue()))
            .toList();
    }

    /**
     * Pasa un mapa de categorías y cantidades a una lista de CantidadCategoriaDTO
     * @param cantidades El mapa con el nombre de la categoría y su cantidad
     * @return Lista de CantidadCategoriaDTO en el mismo orden del mapa
     */
    public List<CantidadCategoriaDTO> toCantidadCategoriaDTOList(Map<String, Long> cantidades) {
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
    public List<PorcentajeCategoriaDTO> toPorcentajeCategoriaDTOList(
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
    public List<RankingLibroDTO> toRankingLibroDTOList(Map<Libro, Long> ranking) {
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