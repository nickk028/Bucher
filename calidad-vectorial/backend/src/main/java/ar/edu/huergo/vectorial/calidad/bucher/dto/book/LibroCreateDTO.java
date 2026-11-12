package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroCreateDTO {

    // Título del libro
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 dígitos.")
    private String titulo;

    // Descripción del libro
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(
        min = 2,
        max = 800,
        message = "La descripción del libro debe tener entre 2 y 800 dígitos."
    )
    private String descripcion;

    // Cantidad de páginas del libro
    @Positive(message = "La cantidad de páginas debe ser mayor a 0.")
    private int paginas;

    // Edición del libro
    @NotBlank(message = "La edición es obligatoria.")
    @Size(min = 2, max = 100, message = "La edición debe tener entre 2 y 100 dígitos.")
    private String edicion;

    // Categoría del libro
    @NotNull(message = "La categoría es obligatoria.")
    @NotEmpty(message = "La categoría es obligatoria.")
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categorias;
}