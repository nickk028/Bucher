package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de Editorial
@Service
public class EditorialService {

    // Id de la editorial propia de los libros creados por los usuarios, cargada por seed
    public static final Long ID_EDITORIAL_PROPIA = 1L;

    @Autowired
    private EditorialRepository editorialRepository;

    /**
     * Obtiene la editorial propia de los libros creados por los usuarios
     * @return La editorial propia
     * @throws EntityNotFoundException Si no se encuentra la editorial propia
     */
    public Editorial obtenerEditorialPropia() throws EntityNotFoundException {
        return editorialRepository
            .findById(ID_EDITORIAL_PROPIA)
            .orElseThrow(() -> new EntityNotFoundException("Editorial propia no encontrada"));
    }

    /**
     * Verifica si una editorial es la editorial propia de los usuarios
     * @param editorial La editorial a verificar
     * @return true si es la editorial propia, false en caso contrario
     */
    public boolean esEditorialPropia(Editorial editorial) {
        return editorial != null && ID_EDITORIAL_PROPIA.equals(editorial.getId());
    }
}