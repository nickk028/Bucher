package ar.edu.huergo.vectorial.calidad.bucher.controller.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.MedioPagoCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.MedioPagoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.payment.MedioPagoMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.MedioPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medioPago")
public class MedioPagoController {

    @Autowired
    private MedioPagoService medioPagoService;

    @Autowired
    private MedioPagoMapper medioPagoMapper;

    /**
    * Obtiene todos los medios de pago
    * @return Lista de todos los medios de pago
    */
    @GetMapping
    public ResponseEntity<List<MedioPagoResponseDTO>> obtenerTodosLosMediosPago() {
        return ResponseEntity.ok(
            medioPagoMapper.toDTOList(medioPagoService.obtenerTodosLosMediosPago()));
    }

    /**
    * Obtiene un medio de pago por su ID
    * @param id El ID del medio de pago a obtener
    * @return El medio de pago correspondiente al ID indicado
    */
    @GetMapping("/{id}")
    public ResponseEntity<MedioPagoResponseDTO> obtenerMedioPagoPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            medioPagoMapper.toDTO(medioPagoService.obtenerMedioPagoPorId(id)));
    }

    /**
    * Obtiene un medio de pago por su nombre
    * @param nombre El nombre del medio de pago a obtener
    * @return El medio de pago correspondiente al nombre indicado
    */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<MedioPagoResponseDTO> obtenerMedioPagoPorNombre(@PathVariable("nombre") String nombre) {
        return ResponseEntity.ok(
            medioPagoMapper.toDTO(medioPagoService.obtenerMedioPagoPorNombre(nombre)));
    }

    /**
    * Crea un nuevo medio de pago
    * @param medioPagoCreateDTO El DTO con los datos del medio de pago a crear
    * @return El medio de pago creado
    */
    @PostMapping("/crear")
    public ResponseEntity<MedioPagoResponseDTO> crearMedioPago(@Valid @RequestBody MedioPagoCreateDTO medioPagoCreateDTO) {
        MedioPago medioPagoNuevo = medioPagoMapper.toEntity(medioPagoCreateDTO);
        MedioPago medioPagoCreado = medioPagoService.crearMedioPago(medioPagoNuevo);
        return ResponseEntity.ok(medioPagoMapper.toDTO(medioPagoCreado));
    }
}