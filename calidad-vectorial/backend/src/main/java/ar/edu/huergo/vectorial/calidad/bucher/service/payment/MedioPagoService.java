package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.MedioPagoRepository;
import jakarta.persistence.EntityNotFoundException;

//Clase que maneja la lógica de MedioPago
@Service
public class MedioPagoService {

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
        return medioPagoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Medio de pago no encontrado"));
    }

    /**
    * Obtiene un medio de pago por su nombre
    * @param nombre El nombre del medio de pago
    * @return El medio de pago correspondiente al nombre
    * @throws EntityNotFoundException Si no se encuentra el medio de pago
    */
    public MedioPago obtenerMedioPagoPorNombre(String nombre) throws EntityNotFoundException {
        return medioPagoRepository.findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new EntityNotFoundException("Medio de pago no encontrado"));
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