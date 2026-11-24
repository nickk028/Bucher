package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.PaginacionUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de la tabla de préstamos activos del panel de administración
@Service
public class TablaEstadisticaRegistroPrestamoService {

    // Estados de la publicación en los que el préstamo se considera activo
    private static final List<Estado> ESTADOS_PRESTAMO_ACTIVO = List.of(
        Estado.antregar,
        Estado.entregado,
        Estado.adevolver,
        Estado.demorado,
        Estado.enrevision,
        Estado.retirado
    );

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    /**
     * Obtiene una página de registros de préstamo activos, ordenados del más reciente al más antiguo
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de registros por página
     * @return La página de registros de préstamo activos
     */
    public Page<RegistroPrestamo> obtenerPrestamosActivos(int pagina, int tamanio) {
        Pageable pageable = PaginacionUtil.crearPageable(
            pagina,
            tamanio,
            Sort.by(Sort.Direction.DESC, "fechaPrestamo", "id")
        );
        return registroPrestamoRepository.findAllByPublicacionEstadoPublicacionIn(
            ESTADOS_PRESTAMO_ACTIVO,
            pageable
        );
    }
}