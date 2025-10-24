package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Clase que maneja la lógica de Libro
 */
@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    RegistroPrestamoService registroPrestamoService;

    public Set<Libro> obtenerTodosLosLibros() {
        return new HashSet<> (libroRepository.findAll());
    }

    public Libro obtenerLibroPorId(Long id) throws EntityNotFoundException {
        return libroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
     * Obtiene un Libro por su titulo
     * @param titulo El titulo del Libro a buscar
     * @return El Libro encontrado
     * @throws EntityNotFoundException No encuentra el Libro
     */
    public Libro obtenerLibroPorTitulo(String titulo) throws EntityNotFoundException {
        return libroRepository.findByTituloIgnoringCase(titulo)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
     * Obtiene Libros por su categoria
     * @param categoria La categoria de los libros a buscar
     * @return Los libros de la categoria
     */
    public Set<Libro> obtenerLibrosPorCategoria(Categoria categoria) {
        return new HashSet<> (libroRepository.findAllByCategoriaContaining(categoria));
    }


    public Set<Libro> obtenerLibrosMasPrestadosDeLaSemana() {
        List<RegistroPrestamo> prestamosDeLaSemana = registroPrestamoService.obtenerRegistrosPrestamosDeLaSemana();

        Map<Libro,Integer> contadorPrestamos = new HashMap<>();

        for (RegistroPrestamo p : prestamosDeLaSemana) {
            Libro libro = p.getPublicacion().getLibro();
            if (contadorPrestamos.containsKey(libro)) {
                contadorPrestamos.put(libro, contadorPrestamos.get(libro) + 1);
            } else {
                contadorPrestamos.put(libro, 1);
            }
        }

        return contadorPrestamos.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).limit(10).map(Map.Entry::getKey).collect(Collectors.toSet());
    }
}