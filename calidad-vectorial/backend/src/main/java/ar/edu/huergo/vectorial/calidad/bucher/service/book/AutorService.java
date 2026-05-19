package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import jakarta.persistence.EntityNotFoundException;


//Clase que maneja la lógica de Autor
@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    /**
    * Obtiene todos los autores
    * @return Lista con todos los autores
    */
    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    /**
    * Obtiene un autor por su ID
    * @param id ID del autor
    * @return Autor encontrado
    * @throws EntityNotFoundException Si el autor no es encontrado
    */
    public Autor obtenerAutorPorId(Long id) throws EntityNotFoundException {
        return autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));
    }

    /**
    * Guarda un nuevo autor con la información del usuario
    * @param autor
    * @return El autor creado
    */
    public Autor crearAutor(Autor autor) {
        return autorRepository.save(autor);
    }
}