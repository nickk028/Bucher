package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.EstadisticasUtil;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de las estadísticas de préstamos del panel de administración
@Service
public class PanelPrestamosService {

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    /**
     * Obtiene la cantidad de préstamos totales
     * @return La cantidad de préstamos
     */
    public long obtenerPrestamosTotales() {
        return registroPrestamoRepository.count();
    }

    /**
     * Obtiene la cantidad de préstamos activos (los que tienen la publicación en estado Prestado)
     * @return La cantidad de préstamos activos
     */
    public long obtenerPrestamosActivos() {
        return registroPrestamoRepository.countByPublicacionEstadoPublicacion(Estado.Prestado);
    }

    /**
     * Obtiene la cantidad de préstamos incompletos (los que todavía no tienen fecha de devolución)
     * @return La cantidad de préstamos incompletos
     */
    public long obtenerPrestamosIncompletos() {
        return registroPrestamoRepository.countByFechaDevolucionIsNull();
    }

    /**
     * Obtiene el porcentaje de préstamos activos sobre el total de préstamos
     * @return El porcentaje sobre el total de préstamos (0 a 1)
     */
    public double obtenerPorcentajePrestamosActivos() {
        return EstadisticasUtil.calcular(obtenerPrestamosActivos(), obtenerPrestamosTotales());
    }

    /**
     * Obtiene la cantidad de préstamos completados en cada mes del año actual, de enero a diciembre
     * @return Mapa ordenado con el nombre del mes y su cantidad de préstamos completados
     */
    public Map<String, Long> obtenerCantidadPrestamosCompletadosPorMes() {
        Map<Integer, Long> cantidadesPorMes = registroPrestamoRepository
            .contarPrestamosCompletadosPorMes(LocalDate.now().getYear())
            .stream()
            .collect(
                Collectors.toMap(
                    PublicacionSocialRepository.ConteoMes::getMes,
                    PublicacionSocialRepository.ConteoMes::getCantidad
                )
            );

        return EstadisticasUtil.completarCantidadesPorMes(cantidadesPorMes);
    }

    /**
     * Obtiene la cantidad de préstamos de cada categoría, en el orden del enum.
     * Un libro con varias categorías suma en cada una.
     * @return Mapa con el nombre de cada categoría y su cantidad de préstamos
     */
    public Map<String, Long> obtenerCantidadPrestamosPorCategoria() {
        Map<String, Long> cantidades = new LinkedHashMap<>();
        for (Categoria categoria : Categoria.values()) {
            cantidades.put(
                categoria.getNombre(),
                registroPrestamoRepository.contarPrestamosPorCategoria(categoria)
            );
        }
        return cantidades;
    }
}