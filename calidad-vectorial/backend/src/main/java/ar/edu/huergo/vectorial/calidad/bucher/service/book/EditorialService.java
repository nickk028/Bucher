package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de Editorial
@Service
public class EditorialService {

    // Nombre de la editorial propia de los libros creados por los usuarios
    public static final String NOMBRE_EDITORIAL_PROPIA = "Bücher";

    @Autowired
    private EditorialRepository editorialRepository;

    /**
     * Obtiene la editorial propia de los libros creados por los usuarios, creándola si no existe
     * @return La editorial propia
     */
    public Editorial obtenerEditorialPropia() {
        return editorialRepository
            .findByNombreIgnoringCase(NOMBRE_EDITORIAL_PROPIA)
            .orElseGet(() -> editorialRepository.save(new Editorial(NOMBRE_EDITORIAL_PROPIA, null)));
    }

    /**
     * Verifica si una editorial es la editorial propia de los usuarios
     * @param editorial La editorial a verificar
     * @return true si es la editorial propia, false en caso contrario
     */
    public boolean esEditorialPropia(Editorial editorial) {
        return editorial != null && NOMBRE_EDITORIAL_PROPIA.equalsIgnoreCase(editorial.getNombre());
    }
}