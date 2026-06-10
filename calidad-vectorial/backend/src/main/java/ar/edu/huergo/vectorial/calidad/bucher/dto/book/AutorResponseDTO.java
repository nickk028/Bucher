package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import java.util.ArrayList;
import java.util.List;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class AutorResponseDTO {

    // Id principal
    private Long id;

    // Nombre del autor
    private String nombre;

    // Descripción del autor
    private String descripcion;

    // URL de la pagina de Wikipedia del autor
    private String urlWikipedia;

    // URL de la imagen del autor
    private String urlFotoAutor;

    // Lista de libros del autor
    private List<Libro> libros = new ArrayList<>();

    // Nombre del usuario
    private String username;
}