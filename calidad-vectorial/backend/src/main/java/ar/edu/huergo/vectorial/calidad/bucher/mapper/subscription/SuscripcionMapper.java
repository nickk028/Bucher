package ar.edu.huergo.vectorial.calidad.bucher.mapper.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Suscripcion utiizada para pasar de entidad a DTO y de DTO a entidad
public class SuscripcionMapper {

    @Autowired
    private PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    /**
     * Pasa de Suscripcion a entidad SuscripcionResponseDTO
     * @param suscripcion La suscripción a pasar a DTO
     * @return Suscripcion como ResponseDTO
     */
    public SuscripcionResponseDTO toDTO(Suscripcion suscripcion) {
        if (suscripcion == null) {
            return null;
        }
        SuscripcionResponseDTO suscripcionResponseDTO = new SuscripcionResponseDTO();

        suscripcionResponseDTO.setId(suscripcion.getId());
        suscripcionResponseDTO.setNombre(suscripcion.getNombre());
        suscripcionResponseDTO.setPorcentajeCobertura(suscripcion.getPorcentajeCobertura());
        suscripcionResponseDTO.setLimiteLibros(suscripcion.getLimiteLibros());
        suscripcionResponseDTO.setPrecio(
            precioHistoricoSuscripcionService.obtenerPrecioActual(suscripcion)
        );

        return suscripcionResponseDTO;
    }

    /**
     * Pasa de una lista de Suscripcion a una lista de SuscripcionResponseDTO
     * @param suscripciones La lista de suscripciones a convertir
     * @return Lista de SuscripcionResponseDTO
     */
    public List<SuscripcionResponseDTO> toDTOList(List<Suscripcion> suscripciones) {
        if (suscripciones == null) {
            return new ArrayList<>();
        }
        return suscripciones.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Pasa de SuscripcionCreateDTO a entidad Suscripcion
     * @param suscripcionCreateDTO El DTO a transformar en entidad
     * @return Suscripcion como entidad
     */
    public Suscripcion toEntity(SuscripcionCreateDTO suscripcionCreateDTO) {
        if (suscripcionCreateDTO == null) {
            return null;
        }
        Suscripcion suscripcion = new Suscripcion();

        suscripcion.setNombre(suscripcionCreateDTO.getNombre());
        suscripcion.setPorcentajeCobertura(suscripcionCreateDTO.getPorcentajeCobertura());
        suscripcion.setLimiteLibros(suscripcionCreateDTO.getLimiteLibros());

        return suscripcion;
    }

    /**
     * Pasa de SuscripcionUpdateDTO a entidad Suscripcion
     * @param suscripcionUpdateDTO El DTO a transformar en entidad
     * @return Suscripcion como entidad
     */
    public Suscripcion toEntity(SuscripcionUpdateDTO suscripcionUpdateDTO) {
        if (suscripcionUpdateDTO == null) {
            return null;
        }
        Suscripcion suscripcion = new Suscripcion();

        suscripcion.setNombre(suscripcionUpdateDTO.getNombre());
        suscripcion.setPorcentajeCobertura(suscripcionUpdateDTO.getPorcentajeCobertura());
        suscripcion.setLimiteLibros(suscripcionUpdateDTO.getLimiteLibros());

        return suscripcion;
    }
}