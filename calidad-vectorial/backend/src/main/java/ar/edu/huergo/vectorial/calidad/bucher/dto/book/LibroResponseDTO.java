package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos.")
    private String titulo;

    // URL de la foto del libro
    private String urlFoto;

    // Descripción del libro
    @NotBlank(message = "La descripción es obligatorio.")
    @Size(min = 2, max = 800, message = "La libro debe tener entre 2 y 800 digitos.")
    private String descripcion;

    // Categoría del libro
    @NotNull(message = "La categoria es obligatoria.")
    @NotEmpty(message = "La categoria es obligatoria.")
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categorias;

    // Nombre del autor
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 digitos")
    private String nombreAutor;

    // Descripción del autor
    @Size(max = 255, message = "El nombre debe tener como máximo 100 digitos")
    private String descripcionAutor;

    // URL de la pagina de Wikipedia del autor
    private String urlWikipediaAutor;

    // URL del autor
    private String urlFotoAutor;
}
