package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoLibro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoLibroRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de PrecioHistoricoLibro
@Service
public class PrecioHistoricoLibroService {

    @Autowired
    private PrecioHistoricoLibroRepository precioHistoricoLibroRepository;

    /**
     * Crea un nuevo registro de precio histórico para un libro
     * @param libro El libro al que pertenece el precio
     * @param precio El precio a registrar
     * @return El registro de precio histórico creado
     */
    public PrecioHistoricoLibro crearPrecioHistorico(Libro libro, BigDecimal precio) {
        PrecioHistoricoLibro precioHistorico = new PrecioHistoricoLibro();
        precioHistorico.setLibro(libro);
        precioHistorico.setPrecio(precio);

        return precioHistoricoLibroRepository.save(precioHistorico);
    }

    /**
     * Obtiene el último precio histórico registrado para un libro
     * @param libro El libro del cual obtener el último precio
     * @return El último registro de precio histórico
     * @throws EntityNotFoundException Si el libro no tiene precios históricos registrados
     */
    public PrecioHistoricoLibro obtenerUltimoPrecio(Libro libro) throws EntityNotFoundException {
        return precioHistoricoLibroRepository
            .findFirstByLibroOrderByFechaModificacionDescIdDesc(libro)
            .orElseThrow(() ->
                new EntityNotFoundException("El libro no tiene precios históricos registrados")
            );
    }

    /**
     * Obtiene el precio actual de un libro (el último precio histórico registrado)
     * @param libro El libro del cual obtener el precio actual
     * @return El precio actual, o null si el libro no tiene precios históricos registrados
     */
    public BigDecimal obtenerPrecioActual(Libro libro) {
        return precioHistoricoLibroRepository
            .findFirstByLibroOrderByFechaModificacionDescIdDesc(libro)
            .map(PrecioHistoricoLibro::getPrecio)
            .orElse(null);
    }

    /**
     * Obtiene el historial de precios de un libro ordenado del más reciente al más antiguo
     * @param libro El libro del cual obtener el historial
     * @return La lista de precios históricos del libro
     */
    public List<PrecioHistoricoLibro> obtenerHistorialPrecios(Libro libro) {
        return precioHistoricoLibroRepository.findAllByLibroOrderByFechaModificacionDescIdDesc(
            libro
        );
    }
}