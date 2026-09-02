package ar.edu.huergo.vectorial.calidad.bucher.mapper.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.PrecioHistoricoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistorico;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad PrecioHistorico utiizada para pasar de entidad a DTO y de DTO a entidad
public class PrecioHistoricoMapper {

    /**
    * Pasa de PrecioHistorico a entidad PrecioHistoricoResponseDTO
    * @param precioHistorico El precio histórico a pasar a DTO
    * @return PrecioHistorico como ResponseDTO
    */
    public PrecioHistoricoResponseDTO toDTO(PrecioHistorico precioHistorico) {
        if (precioHistorico == null) {
            return null;
        }
        PrecioHistoricoResponseDTO precioHistoricoResponseDTO = new PrecioHistoricoResponseDTO();

        precioHistoricoResponseDTO.setPrecio(precioHistorico.getPrecio());
        precioHistoricoResponseDTO.setFechaModificacion(precioHistorico.getFechaModificacion());

        return precioHistoricoResponseDTO;
    }

    /**
    * Pasa de una lista de PrecioHistorico a una lista de PrecioHistoricoResponseDTO
    * @param preciosHistoricos La lista de precios históricos a convertir
    * @return Lista de PrecioHistoricoResponseDTO
    */
    public List<PrecioHistoricoResponseDTO> toDTOList(List<PrecioHistorico> preciosHistoricos) {
        if (preciosHistoricos == null) {
            return new ArrayList<>();
        }
        return preciosHistoricos
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}