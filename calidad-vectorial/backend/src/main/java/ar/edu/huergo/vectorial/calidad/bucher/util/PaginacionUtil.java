package ar.edu.huergo.vectorial.calidad.bucher.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Clase utilitaria para la paginación.
 * Provee métodos para crear un Pageable con el tamaño de página limitado.
 */
public class PaginacionUtil {

    private static final int TAMANIO_MAXIMO_PAGINA = 50;

    /**
     * Crea el Pageable limitando el tamaño de página
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de elementos por página
     * @param sort El criterio de ordenamiento
     * @return El Pageable correspondiente
     */
    public static Pageable crearPageable(int pagina, int tamanio, Sort sort) {
        return PageRequest.of(
            Math.max(pagina, 0),
            Math.min(Math.max(tamanio, 1), TAMANIO_MAXIMO_PAGINA),
            sort
        );
    }
}