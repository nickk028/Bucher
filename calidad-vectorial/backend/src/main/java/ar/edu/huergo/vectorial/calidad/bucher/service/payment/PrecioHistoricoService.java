package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistorico;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoRepository;
import jakarta.persistence.EntityNotFoundException;

//Clase que maneja la lógica de PrecioHistorico
@Service
public class PrecioHistoricoService {

    @Autowired
    private PrecioHistoricoRepository precioHistoricoRepository;

    /**
    * Crea un nuevo registro de precio histórico para un libro
    * @param libro El libro al que pertenece el precio
    * @param precio El precio a registrar
    * @return El registro de precio histórico creado
    */
    public PrecioHistorico crearPrecioHistorico(Libro libro, double precio) {
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setLibro(libro);
        precioHistorico.setPrecio(precio);

        return precioHistoricoRepository.save(precioHistorico);
    }

    /**
    * Obtiene el último precio histórico registrado para un libro
    * @param libro El libro del cual obtener el último precio
    * @return El último registro de precio histórico
    * @throws EntityNotFoundException Si el libro no tiene precios históricos registrados
    */
    public PrecioHistorico obtenerUltimoPrecio(Libro libro) throws EntityNotFoundException {
        return precioHistoricoRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(libro)
            .orElseThrow(() -> new EntityNotFoundException("El libro no tiene precios históricos registrados"));
    }

    /**
    * Obtiene el historial de precios de un libro ordenado del más reciente al más antiguo
    * @param libro El libro del cual obtener el historial
    * @return La lista de precios históricos del libro
    */
    public List<PrecioHistorico> obtenerHistorialPrecios(Libro libro) {
        return precioHistoricoRepository.findAllByLibroOrderByFechaModificacionDesc(libro);
    }
}