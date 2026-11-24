package ar.edu.huergo.vectorial.calidad.bucher.service.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.SuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de Suscripcion
@Service
public class SuscripcionService {

    // Id de la suscripción gratuita cargada por seed
    public static final Long ID_SUSCRIPCION_GRATUITA = 1L;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    /**
     * Obtiene todas las suscripciones
     * @return Lista con todas las suscripciones
     */
    public List<Suscripcion> obtenerTodasLasSuscripciones() {
        return suscripcionRepository.findAll();
    }

    /**
     * Obtiene una suscripción por su ID
     * @param id El ID de la suscripción
     * @return La suscripción correspondiente al ID
     * @throws EntityNotFoundException Si no se encuentra la suscripción
     */
    public Suscripcion obtenerSuscripcionPorId(Long id) throws EntityNotFoundException {
        return suscripcionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Suscripción no encontrada"));
    }

    /**
     * Obtiene la suscripción gratuita
     * @return La suscripción gratuita
     * @throws EntityNotFoundException Si no se encuentra la suscripción gratuita
     */
    public Suscripcion obtenerSuscripcionGratuita() throws EntityNotFoundException {
        return obtenerSuscripcionPorId(ID_SUSCRIPCION_GRATUITA);
    }

    /**
     * Verifica si una suscripción es la suscripción gratuita
     * @param suscripcion La suscripción a verificar
     * @return true si es la suscripción gratuita, false en caso contrario
     */
    public boolean esSuscripcionGratuita(Suscripcion suscripcion) {
        return suscripcion != null && ID_SUSCRIPCION_GRATUITA.equals(suscripcion.getId());
    }

    /**
     * Crea una nueva suscripción con su precio inicial
     * @param suscripcion La suscripción a crear
     * @param precio El precio inicial de la suscripción
     * @return La suscripción creada
     * @throws IllegalArgumentException Si ya existe una suscripción con ese nombre
     */
    @Transactional
    public Suscripcion crearSuscripcion(Suscripcion suscripcion, BigDecimal precio) {
        if (suscripcionRepository.existsByNombreIgnoreCase(suscripcion.getNombre())) {
            throw new IllegalArgumentException("Ya existe una suscripción con ese nombre");
        }

        Suscripcion suscripcionCreada = suscripcionRepository.save(suscripcion);
        precioHistoricoSuscripcionService.crearPrecioHistorico(suscripcionCreada, precio);

        return suscripcionCreada;
    }

    /**
     * Modifica los datos de una suscripción (sin su precio)
     * @param id El ID de la suscripción a modificar
     * @param suscripcionNueva La suscripción con los nuevos datos
     * @return La suscripción modificada
     * @throws EntityNotFoundException Si no se encuentra la suscripción
     * @throws IllegalArgumentException Si ya existe otra suscripción con ese nombre
     */
    public Suscripcion modificarSuscripcion(Long id, Suscripcion suscripcionNueva)
        throws EntityNotFoundException {
        Suscripcion suscripcionExistente = obtenerSuscripcionPorId(id);

        if (
            !suscripcionExistente.getNombre().equalsIgnoreCase(suscripcionNueva.getNombre()) &&
            suscripcionRepository.existsByNombreIgnoreCase(suscripcionNueva.getNombre())
        ) {
            throw new IllegalArgumentException("Ya existe una suscripción con ese nombre");
        }

        suscripcionExistente.setNombre(suscripcionNueva.getNombre());
        suscripcionExistente.setPorcentajeCobertura(suscripcionNueva.getPorcentajeCobertura());
        suscripcionExistente.setLimiteLibros(suscripcionNueva.getLimiteLibros());

        return suscripcionRepository.save(suscripcionExistente);
    }

    /**
     * Modifica el precio de una suscripción generando un nuevo registro en su historial de precios
     * @param id El ID de la suscripción a modificar
     * @param nuevoPrecio El nuevo precio de la suscripción
     * @return El nuevo registro de precio histórico
     * @throws EntityNotFoundException Si no se encuentra la suscripción
     * @throws IllegalArgumentException Si la suscripción es la gratuita, o si el precio es igual al actual
     */
    public PrecioHistoricoSuscripcion modificarPrecioSuscripcion(Long id, BigDecimal nuevoPrecio)
        throws EntityNotFoundException {
        Suscripcion suscripcion = obtenerSuscripcionPorId(id);

        if (esSuscripcionGratuita(suscripcion)) {
            throw new IllegalArgumentException(
                "El precio de la suscripción gratuita no se puede modificar"
            );
        }

        BigDecimal precioActual = precioHistoricoSuscripcionService.obtenerPrecioActual(
            suscripcion
        );
        if (precioActual != null && precioActual.compareTo(nuevoPrecio) == 0) {
            throw new IllegalArgumentException("El nuevo precio no puede ser igual al precio actual");
        }

        return precioHistoricoSuscripcionService.crearPrecioHistorico(suscripcion, nuevoPrecio);
    }
}