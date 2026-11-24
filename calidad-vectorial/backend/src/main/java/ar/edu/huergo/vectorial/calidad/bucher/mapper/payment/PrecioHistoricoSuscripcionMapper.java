package ar.edu.huergo.vectorial.calidad.bucher.mapper.payment;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.PrecioHistoricoSuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad PrecioHistoricoSuscripcion utiizada para pasar de entidad a DTO y de DTO a entidad
public class PrecioHistoricoSuscripcionMapper {

    /**
     * Pasa de PrecioHistoricoSuscripcion a entidad PrecioHistoricoSuscripcionResponseDTO
     * @param precioHistorico El precio histórico a pasar a DTO
     * @return PrecioHistoricoSuscripcion como ResponseDTO
     */
    public PrecioHistoricoSuscripcionResponseDTO toDTO(PrecioHistoricoSuscripcion precioHistorico) {
        if (precioHistorico == null) {
            return null;
        }
        PrecioHistoricoSuscripcionResponseDTO precioHistoricoResponseDTO =
            new PrecioHistoricoSuscripcionResponseDTO();

        precioHistoricoResponseDTO.setPrecio(precioHistorico.getPrecio());
        precioHistoricoResponseDTO.setFechaModificacion(precioHistorico.getFechaModificacion());

        return precioHistoricoResponseDTO;
    }
}