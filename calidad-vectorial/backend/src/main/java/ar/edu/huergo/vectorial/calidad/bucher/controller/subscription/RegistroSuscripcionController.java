package ar.edu.huergo.vectorial.calidad.bucher.controller.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.RegistroSuscripcionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.RegistroSuscripcionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.subscription.SuscripcionActivaResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.subscription.RegistroSuscripcionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.subscription.RegistroSuscripcionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro-suscripcion")
public class RegistroSuscripcionController {

    @Autowired
    private RegistroSuscripcionService registroSuscripcionService;

    @Autowired
    private RegistroSuscripcionMapper registroSuscripcionMapper;

    /**
     * Obtiene la suscripción activa del usuario autenticado
     * @param usuarioAutenticado El usuario autenticado
     * @return La suscripción activa con sus fechas
     */
    @GetMapping("/activa")
    public ResponseEntity<SuscripcionActivaResponseDTO> obtenerSuscripcionActiva(
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            registroSuscripcionMapper.toActivaDTO(
                registroSuscripcionService.obtenerRegistroActivo(usuarioAutenticado.getUsername())
            )
        );
    }

    /**
     * Obtiene los registros de suscripción del usuario autenticado
     * @param usuarioAutenticado El usuario autenticado
     * @return La lista de registros de suscripción del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<List<RegistroSuscripcionResponseDTO>> obtenerRegistros(
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            registroSuscripcionMapper.toDTOList(
                registroSuscripcionService.obtenerRegistrosPorUsuario(
                    usuarioAutenticado.getUsername()
                )
            )
        );
    }

    /**
     * Obtiene un registro de suscripción por su ID (el admin puede ver cualquiera, el usuario solo el suyo)
     * @param id El ID del registro de suscripción
     * @param usuarioAutenticado El usuario autenticado
     * @return El registro de suscripción buscado
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistroSuscripcionResponseDTO> obtenerRegistro(
        @PathVariable("id") Long id,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        boolean esAdmin = usuarioAutenticado
            .getAuthorities()
            .stream()
            .anyMatch((autoridad) -> autoridad.getAuthority().equals("ROLE_ADMIN"));

        if (esAdmin) {
            return ResponseEntity.ok(
                registroSuscripcionMapper.toDTO(registroSuscripcionService.obtenerRegistroPorId(id))
            );
        }
        return ResponseEntity.ok(
            registroSuscripcionMapper.toDTO(
                registroSuscripcionService.obtenerRegistroPorIdYUsuario(
                    id,
                    usuarioAutenticado.getUsername()
                )
            )
        );
    }

    /**
     * Contrata una suscripción paga para el usuario autenticado
     * @param registroSuscripcionCreateDTO El DTO con la suscripción, el medio de pago y los datos de la tarjeta
     * @param usuarioAutenticado El usuario autenticado
     * @return El registro de suscripción creado
     */
    @PostMapping("/contratar")
    public ResponseEntity<RegistroSuscripcionResponseDTO> contratarSuscripcion(
        @Valid @RequestBody RegistroSuscripcionCreateDTO registroSuscripcionCreateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            registroSuscripcionMapper.toDTO(
                registroSuscripcionService.contratarSuscripcion(
                    usuarioAutenticado.getUsername(),
                    registroSuscripcionCreateDTO.getSuscripcionId(),
                    registroSuscripcionCreateDTO.getMedioPagoId(),
                    registroSuscripcionCreateDTO.getNumeroTarjeta(),
                    registroSuscripcionCreateDTO.getVencimiento(),
                    registroSuscripcionCreateDTO.getCvv(),
                    registroSuscripcionCreateDTO.getTitular()
                )
            )
        );
    }

    /**
     * Cancela las suscripciones pagas del usuario autenticado y lo vuelve a la suscripción gratuita
     * @param usuarioAutenticado El usuario autenticado
     * @return El registro de la suscripción gratuita creado
     */
    @PostMapping("/cancelar")
    public ResponseEntity<RegistroSuscripcionResponseDTO> cancelarSuscripcion(
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            registroSuscripcionMapper.toDTO(
                registroSuscripcionService.cancelarSuscripcion(usuarioAutenticado.getUsername())
            )
        );
    }
}