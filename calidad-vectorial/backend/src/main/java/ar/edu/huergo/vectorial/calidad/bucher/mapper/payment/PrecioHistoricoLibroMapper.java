package ar.edu.huergo.vectorial.calidad.bucher.mapper.payment;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.PrecioHistoricoLibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoLibro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad PrecioHistoricoLibro utiizada para pasar de entidad a DTO y de DTO a entidad
public class PrecioHistoricoLibroMapper {

    /**
     * Pasa de PrecioHistoricoLibro a entidad PrecioHistoricoLibroResponseDTO
     * @param precioHistorico El precio histórico a pasar a DTO
     * @return PrecioHistoricoLibro como ResponseDTO
     */
    public PrecioHistoricoLibroResponseDTO toDTO(PrecioHistoricoLibro precioHistorico) {
        if (precioHistorico == null) {
            return null;
        }
        PrecioHistoricoLibroResponseDTO precioHistoricoResponseDTO =
            new PrecioHistoricoLibroResponseDTO();

        precioHistoricoResponseDTO.setPrecio(precioHistorico.getPrecio());
        precioHistoricoResponseDTO.setFechaModificacion(precioHistorico.getFechaModificacion());

        return precioHistoricoResponseDTO;
    }

    /**
     * Pasa de una lista de PrecioHistoricoLibro a una lista de PrecioHistoricoLibroResponseDTO
     * @param preciosHistoricos La lista de precios históricos a convertir
     * @return Lista de PrecioHistoricoLibroResponseDTO
     */
    public List<PrecioHistoricoLibroResponseDTO> toDTOList(
        List<PrecioHistoricoLibro> preciosHistoricos
    ) {
        if (preciosHistoricos == null) {
            return new ArrayList<>();
        }
        return preciosHistoricos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}