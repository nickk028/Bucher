package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroSocialDTO {

    // Id principal
    private Long id;

    // Título del libro
    private String titulo;

    // URL de la foto del libro
    private String urlFoto;

    // Calificación del libro
    private int calificacion;

    // Nombre del autor
    private String nombreAutor;

    // Categorías del libro
    private Set<Categoria> categorias;
}