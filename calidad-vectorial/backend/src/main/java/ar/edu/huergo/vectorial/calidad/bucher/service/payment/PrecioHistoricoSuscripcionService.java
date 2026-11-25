package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoSuscripcionRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de PrecioHistoricoSuscripcion
@Service
public class PrecioHistoricoSuscripcionService {

    @Autowired
    private PrecioHistoricoSuscripcionRepository precioHistoricoSuscripcionRepository;

    /**
     * Crea un nuevo registro de precio histórico para una suscripción
     * @param suscripcion La suscripción a la que pertenece el precio
     * @param precio El precio a registrar
     * @return El registro de precio histórico creado
     */
    public PrecioHistoricoSuscripcion crearPrecioHistorico(
        Suscripcion suscripcion,
        BigDecimal precio
    ) {
        PrecioHistoricoSuscripcion precioHistorico = new PrecioHistoricoSuscripcion();
        precioHistorico.setSuscripcion(suscripcion);
        precioHistorico.setPrecio(precio);

        return precioHistoricoSuscripcionRepository.save(precioHistorico);
    }

    /**
     * Obtiene el precio actual de una suscripción (el último precio histórico registrado)
     * @param suscripcion La suscripción de la cual obtener el precio actual
     * @return El precio actual, o null si la suscripción no tiene precios históricos registrados
     */
    public BigDecimal obtenerPrecioActual(Suscripcion suscripcion) {
        return precioHistoricoSuscripcionRepository
            .findFirstBySuscripcionOrderByFechaModificacionDescIdDesc(suscripcion)
            .map(PrecioHistoricoSuscripcion::getPrecio)
            .orElse(null);
    }
}