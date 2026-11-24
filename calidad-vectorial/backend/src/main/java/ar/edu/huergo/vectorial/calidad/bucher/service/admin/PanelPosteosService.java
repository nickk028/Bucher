package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.EstadisticasUtil;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de las estadísticas de posteos del panel de administración
@Service
public class PanelPosteosService {

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Obtiene la cantidad de posteos publicados (incluidos los eliminados)
     * @return La cantidad de posteos
     */
    public long obtenerPosteosPublicados() {
        return publicacionSocialRepository.count();
    }

    /**
     * Obtiene la cantidad de posteos que responden a otros posteos (incluidos los eliminados)
     * @return La cantidad de posteos con referencias
     */
    public long obtenerPosteosConReferencias() {
        return publicacionSocialRepository.countByPublicacionReferenciadaIsNotNull();
    }

    /**
     * Obtiene la cantidad de posteos denunciados que no fueron eliminados
     * @return La cantidad de posteos denunciados
     */
    public long obtenerPosteosDenunciados() {
        return publicacionSocialRepository.countByReportesGreaterThanAndRegistroEliminacionIsNull(0);
    }

    /**
     * Obtiene el porcentaje de posteos denunciados sobre el total de posteos (incluidos los eliminados)
     * @return El porcentaje sobre el total de posteos (0 a 1)
     */
    public double obtenerPorcentajePosteosDenunciados() {
        return EstadisticasUtil.calcular(obtenerPosteosDenunciados(), obtenerPosteosPublicados());
    }

    /**
     * Obtiene la cantidad de posteos eliminados
     * @return La cantidad de posteos eliminados
     */
    public long obtenerPosteosEliminados() {
        return publicacionSocialRepository.countByRegistroEliminacionIsNotNull();
    }

    /**
     * Obtiene el porcentaje de posteos eliminados sobre el total de posteos
     * @return El porcentaje sobre el total de posteos (0 a 1)
     */
    public double obtenerPorcentajePosteosEliminados() {
        return EstadisticasUtil.calcular(obtenerPosteosEliminados(), obtenerPosteosPublicados());
    }

    /**
     * Obtiene el porcentaje de posteos con libro (incluidas las respuestas que lo heredan) sobre el total de posteos
     * @return El porcentaje sobre el total de posteos (0 a 1)
     */
    public double obtenerPorcentajePosteosConMenciones() {
        return EstadisticasUtil.calcular(
            publicacionSocialRepository.countByLibroIsNotNull(),
            obtenerPosteosPublicados()
        );
    }

    /**
     * Obtiene la cantidad de posteos de cada mes del año actual, de enero a diciembre
     * @return Mapa ordenado con el nombre del mes y su cantidad de posteos
     */
    public Map<String, Long> obtenerCantidadPosteosPorMes() {
        Map<Integer, Long> cantidadesPorMes = publicacionSocialRepository
            .contarPosteosPorMes(LocalDate.now().getYear())
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
     * Obtiene el porcentaje de las 5 categorías más mencionadas y de las restantes agrupadas en "Otras".
     * Solo cuentan los posteos raíz (eliminados incluidos). Un libro con varias categorías suma en cada una, y el porcentaje se calcula sobre la suma de todas.
     * @return Mapa ordenado de mayor a menor con el nombre de la categoría y su porcentaje
     */
    public Map<String, Double> obtenerCategoriasMasMencionadas() {
        Map<Categoria, Long> cantidades = new EnumMap<>(Categoria.class);
        for (Categoria categoria : Categoria.values()) {
            cantidades.put(
                categoria,
                publicacionSocialRepository.contarPosteosRaizPorCategoria(categoria)
            );
        }

        return EstadisticasUtil.calcularCategoriasDestacadas(cantidades);
    }

    /**
     * Obtiene los 5 libros más mencionados en posteos raíz (eliminados incluidos)
     * @return Mapa ordenado de mayor a menor con el libro y la cantidad de posteos que lo mencionan
     */
    public Map<Libro, Long> obtenerLibrosMasMencionados() {
        List<LibroUsuarioRepository.ConteoLibro> conteos =
            publicacionSocialRepository.contarLibrosMasMencionados(
                PageRequest.of(0, EstadisticasUtil.CANTIDAD_DESTACADOS)
            );

        Map<Long, Libro> libros = libroRepository
            .findAllById(conteos.stream().map(LibroUsuarioRepository.ConteoLibro::getLibroId).toList())
            .stream()
            .collect(Collectors.toMap(Libro::getId, (libro) -> libro));

        Map<Libro, Long> ranking = new LinkedHashMap<>();
        for (LibroUsuarioRepository.ConteoLibro conteo : conteos) {
            ranking.put(libros.get(conteo.getLibroId()), conteo.getCantidad());
        }
        return ranking;
    }
}