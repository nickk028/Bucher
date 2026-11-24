package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.MedioPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de MedioPago
@Service
public class MedioPagoService {

    // Id del medio de pago utilizado en las suscripciones gratuitas, cargado por seed
    public static final Long ID_MEDIO_PAGO_SIN_PAGO = 1L;

    // Id del medio de pago Visa, cargado por seed
    public static final Long ID_MEDIO_PAGO_VISA = 2L;

    @Autowired
    private MedioPagoRepository medioPagoRepository;

    /**
     * Obtiene todos los medios de pago
     * @return Lista con todos los medios de pago
     */
    public List<MedioPago> obtenerTodosLosMediosPago() {
        return medioPagoRepository.findAll();
    }

    /**
     * Obtiene un medio de pago por su ID
     * @param id El ID del medio de pago
     * @return El medio de pago correspondiente al ID
     * @throws EntityNotFoundException Si no se encuentra el medio de pago
     */
    public MedioPago obtenerMedioPagoPorId(Long id) throws EntityNotFoundException {
        return medioPagoRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Medio de pago no encontrado"));
    }

    /**
     * Obtiene un medio de pago por su nombre
     * @param nombre El nombre del medio de pago
     * @return El medio de pago correspondiente al nombre
     * @throws EntityNotFoundException Si no se encuentra el medio de pago
     */
    public MedioPago obtenerMedioPagoPorNombre(String nombre) throws EntityNotFoundException {
        return medioPagoRepository
            .findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new EntityNotFoundException("Medio de pago no encontrado"));
    }

    /**
     * Obtiene el medio de pago utilizado en las suscripciones gratuitas
     * @return El medio de pago sin pago
     * @throws EntityNotFoundException Si no se encuentra el medio de pago
     */
    public MedioPago obtenerMedioPagoSinPago() throws EntityNotFoundException {
        return obtenerMedioPagoPorId(ID_MEDIO_PAGO_SIN_PAGO);
    }

    /**
     * Verifica si un medio de pago es Visa
     * @param medioPago El medio de pago a verificar
     * @return true si es Visa, false en caso contrario
     */
    public boolean esMedioPagoVisa(MedioPago medioPago) {
        return medioPago != null && ID_MEDIO_PAGO_VISA.equals(medioPago.getId());
    }

    /**
     * Crea un nuevo medio de pago
     * @param medioPago El medio de pago a crear
     * @return El medio de pago creado
     */
    public MedioPago crearMedioPago(MedioPago medioPago) {
        return medioPagoRepository.save(medioPago);
    }
}