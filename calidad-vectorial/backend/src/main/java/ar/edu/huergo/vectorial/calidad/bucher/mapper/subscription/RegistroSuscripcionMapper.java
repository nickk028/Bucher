package ar.edu.huergo.vectorial.calidad.bucher.mapper.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.RegistroSuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionActivaResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.RegistroSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad RegistroSuscripcion utiizada para pasar de entidad a DTO y de DTO a entidad
public class RegistroSuscripcionMapper {

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    @Autowired
    private PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    /**
     * Pasa de RegistroSuscripcion a entidad RegistroSuscripcionResponseDTO
     * @param registroSuscripcion El registro a pasar a DTO
     * @return RegistroSuscripcion como ResponseDTO
     */
    public RegistroSuscripcionResponseDTO toDTO(RegistroSuscripcion registroSuscripcion) {
        if (registroSuscripcion == null) {
            return null;
        }
        RegistroSuscripcionResponseDTO registroResponseDTO = new RegistroSuscripcionResponseDTO();

        registroResponseDTO.setId(registroSuscripcion.getId());
        registroResponseDTO.setUsuario(registroSuscripcion.getUsuario().getUsername());
        registroResponseDTO.setSuscripcion(
            suscripcionMapper.toDTO(registroSuscripcion.getSuscripcion())
        );
        registroResponseDTO.setMedioPago(registroSuscripcion.getMedioPago().getNombre());
        registroResponseDTO.setFechaInicio(registroSuscripcion.getFechaInicio());
        registroResponseDTO.setFechaFin(registroSuscripcion.getFechaFin());
        registroResponseDTO.setPrecioBase(registroSuscripcion.getPrecioBase());
        registroResponseDTO.setMontoPagado(registroSuscripcion.getMontoPagado());
        registroResponseDTO.setMontoAcreditado(registroSuscripcion.getMontoAcreditado());
        registroResponseDTO.setFechaTransaccion(registroSuscripcion.getFechaTransaccion());

        return registroResponseDTO;
    }

    /**
     * Pasa de una lista de RegistroSuscripcion a una lista de RegistroSuscripcionResponseDTO
     * @param registrosSuscripcion La lista de registros a convertir
     * @return Lista de RegistroSuscripcionResponseDTO
     */
    public List<RegistroSuscripcionResponseDTO> toDTOList(
        List<RegistroSuscripcion> registrosSuscripcion
    ) {
        if (registrosSuscripcion == null) {
            return new ArrayList<>();
        }
        return registrosSuscripcion.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Pasa de RegistroSuscripcion a SuscripcionActivaResponseDTO
     * @param registroSuscripcion El registro de la suscripción activa
     * @return SuscripcionActivaResponseDTO con los datos de la suscripción y sus fechas
     */
    public SuscripcionActivaResponseDTO toActivaDTO(RegistroSuscripcion registroSuscripcion) {
        if (registroSuscripcion == null) {
            return null;
        }
        SuscripcionActivaResponseDTO suscripcionActivaDTO = new SuscripcionActivaResponseDTO();

        suscripcionActivaDTO.setNombre(registroSuscripcion.getSuscripcion().getNombre());
        suscripcionActivaDTO.setPorcentajeCobertura(
            registroSuscripcion.getSuscripcion().getPorcentajeCobertura()
        );
        suscripcionActivaDTO.setLimiteLibros(registroSuscripcion.getSuscripcion().getLimiteLibros());
        suscripcionActivaDTO.setPrecio(
            precioHistoricoSuscripcionService.obtenerPrecioActual(registroSuscripcion.getSuscripcion())
        );
        suscripcionActivaDTO.setFechaInicio(registroSuscripcion.getFechaInicio());
        suscripcionActivaDTO.setFechaFin(registroSuscripcion.getFechaFin());

        return suscripcionActivaDTO;
    }
}