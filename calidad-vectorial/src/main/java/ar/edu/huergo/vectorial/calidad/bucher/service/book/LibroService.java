package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Clase que maneja la lógica de Libro
 */
@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;

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
}