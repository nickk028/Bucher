package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.EstadisticasUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de las estadísticas de usuarios del panel de administración
@Service
public class PanelUsuariosService {

    // Estados de la publicación en los que el préstamo se considera activo
    private static final List<Estado> ESTADOS_PRESTAMO_ACTIVO = List.of(
        Estado.aentregar,
        Estado.entregado,
        Estado.adevolver,
        Estado.demorado,
        Estado.enrevision,
        Estado.retirado
    );

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    /**
     * Obtiene la cantidad de usuarios totales (admins incluidos)
     * @return La cantidad de usuarios
     */
    public long obtenerUsuariosTotales() {
        return usuarioRepository.count();
    }

    /**
     * Obtiene la cantidad de usuarios creados en el año actual
     * @return La cantidad de usuarios nuevos
     */
    public long obtenerUsuariosNuevos() {
        return usuarioRepository.countByFechaCreacionGreaterThanEqual(
            LocalDate.now().withDayOfYear(1).atStartOfDay()
        );
    }

    /**
     * Obtiene la cantidad de usuarios creados en cada mes del año actual, de enero a diciembre
     * @return Mapa ordenado con el nombre del mes y su cantidad de usuarios nuevos
     */
    public Map<String, Long> obtenerCantidadUsuariosNuevosPorMes() {
        Map<Integer, Long> cantidadesPorMes = usuarioRepository
            .contarUsuariosNuevosPorMes(LocalDate.now().getYear())
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
     * Obtiene el porcentaje de usuarios activos (los que ingresaron en el último mes) sobre el total de usuarios
     * @return El porcentaje sobre el total de usuarios (0 a 1)
     */
    public double obtenerPorcentajeUsuariosActivos() {
        return EstadisticasUtil.calcular(
            obtenerUsuariosTotales() - obtenerUsuariosInactivos(),
            obtenerUsuariosTotales()
        );
    }

    /**
     * Obtiene el porcentaje de usuarios inactivos (los que llevan un mes sin ingresar) sobre el total de usuarios
     * @return El porcentaje sobre el total de usuarios (0 a 1)
     */
    public double obtenerPorcentajeUsuariosInactivos() {
        return EstadisticasUtil.calcular(obtenerUsuariosInactivos(), obtenerUsuariosTotales());
    }

    /**
     * Obtiene el porcentaje de usuarios con préstamos activos (dueños de publicaciones en préstamo) sobre el total de usuarios
     * @return El porcentaje sobre el total de usuarios (0 a 1)
     */
    public double obtenerPorcentajeUsuariosConPrestamosActivos() {
        return EstadisticasUtil.calcular(
            publicacionRepository.contarUsuariosConPublicacionesEnEstados(ESTADOS_PRESTAMO_ACTIVO),
            obtenerUsuariosTotales()
        );
    }

    /**
     * Obtiene el porcentaje de usuarios con al menos una publicación sobre el total de usuarios
     * @return El porcentaje sobre el total de usuarios (0 a 1)
     */
    public double obtenerPorcentajeUsuariosConPublicaciones() {
        return EstadisticasUtil.calcular(
            publicacionRepository.contarUsuariosConPublicaciones(),
            obtenerUsuariosTotales()
        );
    }

    /**
     * Obtiene el porcentaje de usuarios con al menos un posteo (incluidos los eliminados) sobre el total de usuarios
     * @return El porcentaje sobre el total de usuarios (0 a 1)
     */
    public double obtenerPorcentajeUsuariosConPosteos() {
        return EstadisticasUtil.calcular(
            publicacionSocialRepository.contarUsuariosConPosteos(),
            obtenerUsuariosTotales()
        );
    }

    /**
     * Obtiene la cantidad de usuarios inactivos (los que llevan más de un mes sin ingresar)
     * @return La cantidad de usuarios inactivos
     */
    private long obtenerUsuariosInactivos() {
        return usuarioRepository.countByUltimoAccesoBefore(LocalDateTime.now().minusMonths(1));
    }
}