package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.EditorialService;
import ar.edu.huergo.vectorial.calidad.bucher.util.EstadisticasUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de las estadísticas de libros del panel de administración
@Service
public class PanelLibrosService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroUsuarioRepository libroUsuarioRepository;

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private EditorialService editorialService;

    /**
     * Obtiene la cantidad de libros registrados
     * @return La cantidad de libros
     */
    public long obtenerLibrosRegistrados() {
        return libroRepository.count();
    }

    /**
     * Obtiene la cantidad de libros independientes (los de la editorial propia de los usuarios)
     * @return La cantidad de libros independientes
     * @throws EntityNotFoundException Si no se encuentra la editorial propia
     */
    public long obtenerLibrosIndependientes() throws EntityNotFoundException {
        return libroRepository.countByEditorial(editorialService.obtenerEditorialPropia());
    }

    /**
     * Obtiene la cantidad de libros de cada categoría, en el orden del enum
     * @return Mapa con el nombre de cada categoría y su cantidad de libros
     */
    public Map<String, Long> obtenerCantidadLibrosPorCategoria() {
        Map<String, Long> cantidades = new LinkedHashMap<>();
        for (Categoria categoria : Categoria.values()) {
            cantidades.put(categoria.getNombre(), libroRepository.countByCategoriaContaining(categoria));
        }
        return cantidades;
    }

    /**
     * Obtiene el porcentaje de las 5 categorías más guardadas y de las restantes agrupadas en "Otras".
     * Un libro con varias categorías suma en cada una, y el porcentaje se calcula sobre la suma de todas.
     * @return Mapa ordenado de mayor a menor con el nombre de la categoría y su porcentaje
     */
    public Map<String, Double> obtenerCategoriasMasGuardadas() {
        Map<Categoria, Long> cantidades = new EnumMap<>(Categoria.class);
        for (Categoria categoria : Categoria.values()) {
            cantidades.put(categoria, libroUsuarioRepository.contarPorCategoria(categoria));
        }

        return EstadisticasUtil.calcularCategoriasDestacadas(cantidades);
    }

    /**
     * Obtiene los 5 libros más guardados por los usuarios
     * @return Mapa ordenado de mayor a menor con el libro y la cantidad de usuarios que lo guardaron
     */
    public Map<Libro, Long> obtenerLibrosMasGuardados() {
        List<LibroUsuarioRepository.ConteoLibro> conteos =
            libroUsuarioRepository.contarLibrosMasGuardados(
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

    /**
     * Obtiene el porcentaje de libros guardados por al menos un usuario
     * @return El porcentaje sobre el total de libros (0 a 1)
     */
    public double obtenerPorcentajeLibrosGuardados() {
        return EstadisticasUtil.calcularPorcentaje(
            libroUsuarioRepository.contarLibrosGuardados(),
            libroRepository.count()
        );
    }

    /**
     * Obtiene el porcentaje de libros nombrados en al menos un posteo (incluidos los eliminados)
     * @return El porcentaje sobre el total de libros (0 a 1)
     */
    public double obtenerPorcentajeLibrosNombradosEnPosteos() {
        return EstadisticasUtil.calcularPorcentaje(
            publicacionSocialRepository.contarLibrosNombrados(),
            libroRepository.count()
        );
    }

    /**
     * Obtiene el porcentaje de libros con al menos una publicación
     * @return El porcentaje sobre el total de libros (0 a 1)
     */
    public double obtenerPorcentajeLibrosConPublicaciones() {
        return EstadisticasUtil.calcularPorcentaje(
            libroRepository.countByPublicacionesIsNotEmpty(),
            libroRepository.count()
        );
    }
}