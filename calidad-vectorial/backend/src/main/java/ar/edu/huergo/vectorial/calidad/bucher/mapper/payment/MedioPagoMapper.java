package ar.edu.huergo.vectorial.calidad.bucher.mapper.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.MedioPagoCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.MedioPagoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad MedioPago utiizada para pasar de entidad a DTO y de DTO a entidad
public class MedioPagoMapper {

    /**
    * Pasa de MedioPago a entidad MedioPagoResponseDTO
    * @param medioPago El medio de pago a pasar a DTO
    * @return MedioPago como ResponseDTO
    */
    public MedioPagoResponseDTO toDTO(MedioPago medioPago) {
        if (medioPago == null) {
            return null;
        }
        MedioPagoResponseDTO medioPagoResponseDTO = new MedioPagoResponseDTO();

        medioPagoResponseDTO.setId(medioPago.getId());
        medioPagoResponseDTO.setNombre(medioPago.getNombre());
        medioPagoResponseDTO.setPorcentajeComision(medioPago.getPorcentajeComision());

        return medioPagoResponseDTO;
    }

    /**
    * Pasa de una lista de MedioPago a una lista de MedioPagoResponseDTO
    * @param mediosPago La lista de medios de pago a convertir
    * @return Lista de MedioPagoResponseDTO
    */
    public List<MedioPagoResponseDTO> toDTOList(List<MedioPago> mediosPago) {
        if (mediosPago == null) {
            return new ArrayList<>();
        }
        return mediosPago
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
    * Pasa de MedioPagoCreateDTO a entidad MedioPago
    * @param medioPagoCreateDTO El DTO a transformar en entidad
    * @return MedioPago como entidad
    */
    public MedioPago toEntity(MedioPagoCreateDTO medioPagoCreateDTO) {
        if (medioPagoCreateDTO == null) {
            return null;
        }
        MedioPago medioPago = new MedioPago();

        medioPago.setNombre(medioPagoCreateDTO.getNombre());
        medioPago.setPorcentajeComision(medioPagoCreateDTO.getPorcentajeComision());

        return medioPago;
    }
}