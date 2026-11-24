package ar.edu.huergo.vectorial.calidad.bucher.controller.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.PrecioHistoricoSuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionPrecioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.payment.PrecioHistoricoSuscripcionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.subscription.SuscripcionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.subscription.SuscripcionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suscripcion")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    @Autowired
    private PrecioHistoricoSuscripcionMapper precioHistoricoSuscripcionMapper;

    /**
     * Obtiene todas las suscripciones
     * @return Lista de todas las suscripciones
     */
    @GetMapping
    public ResponseEntity<List<SuscripcionResponseDTO>> obtenerTodasLasSuscripciones() {
        return ResponseEntity.ok(
            suscripcionMapper.toDTOList(suscripcionService.obtenerTodasLasSuscripciones())
        );
    }

    /**
     * Crea una nueva suscripción (solo admin)
     * @param suscripcionCreateDTO El DTO con los datos de la suscripción a crear
     * @return La suscripción creada
     */
    @PostMapping("/crear")
    public ResponseEntity<SuscripcionResponseDTO> crearSuscripcion(
        @Valid @RequestBody SuscripcionCreateDTO suscripcionCreateDTO
    ) {
        return ResponseEntity.ok(
            suscripcionMapper.toDTO(
                suscripcionService.crearSuscripcion(
                    suscripcionMapper.toEntity(suscripcionCreateDTO),
                    suscripcionCreateDTO.getPrecio()
                )
            )
        );
    }

    /**
     * Modifica los datos de una suscripción, sin su precio (solo admin)
     * @param id El ID de la suscripción a modificar
     * @param suscripcionUpdateDTO El DTO con los nuevos datos de la suscripción
     * @return La suscripción modificada
     */
    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDTO> modificarSuscripcion(
        @PathVariable("id") Long id,
        @Valid @RequestBody SuscripcionUpdateDTO suscripcionUpdateDTO
    ) {
        return ResponseEntity.ok(
            suscripcionMapper.toDTO(
                suscripcionService.modificarSuscripcion(
                    id,
                    suscripcionMapper.toEntity(suscripcionUpdateDTO)
                )
            )
        );
    }

    /**
     * Modifica el precio de una suscripción (solo admin) y genera un nuevo registro en su historial de precios
     * @param id El ID de la suscripción a modificar
     * @param suscripcionPrecioUpdateDTO El DTO con el nuevo precio de la suscripción
     * @return El nuevo registro de precio histórico de la suscripción
     */
    @PutMapping("precio/{id}")
    public ResponseEntity<PrecioHistoricoSuscripcionResponseDTO> modificarPrecioSuscripcion(
        @PathVariable("id") Long id,
        @Valid @RequestBody SuscripcionPrecioUpdateDTO suscripcionPrecioUpdateDTO
    ) {
        return ResponseEntity.ok(
            precioHistoricoSuscripcionMapper.toDTO(
                suscripcionService.modificarPrecioSuscripcion(
                    id,
                    suscripcionPrecioUpdateDTO.getPrecio()
                )
            )
        );
    }
}