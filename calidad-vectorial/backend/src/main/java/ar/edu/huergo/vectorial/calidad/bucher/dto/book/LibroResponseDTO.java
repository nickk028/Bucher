package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroResponseDTO {

    // Id principal
    private Long id;

    // Título del libro
    private String titulo;

    // URL de la foto del libro
    private String urlFoto;

    // Descripción del libro
    private String descripcion;

    // Precio actual del libro (último precio histórico)
    private double precio;

    // Categoría del libro
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categorias;

    // Nombre del autor
    private String nombreAutor;

    // Descripción del autor
    private String descripcionAutor;

    // URL de la pagina de Wikipedia del autor
    private String urlWikipediaAutor;

    // URL del autor
    private String urlFotoAutor;
}
