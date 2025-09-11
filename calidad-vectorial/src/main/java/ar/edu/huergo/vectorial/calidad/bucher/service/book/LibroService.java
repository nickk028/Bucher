package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;

    public Libro obtenerLibroPorTitulo(String titulo) throws EntityNotFoundException {
        return libroRepository.findByTituloIgnoringCase(titulo)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }
}