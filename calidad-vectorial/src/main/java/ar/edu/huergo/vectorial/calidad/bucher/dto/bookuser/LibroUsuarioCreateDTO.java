package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroUsuarioCreateDTO {
    // Título del libro
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos.")
    private String titulo;

    // Pagina actual de libro que está leyendo el usuario
    @NotNull(message = "La página actual es obligatoria.")
    @PositiveOrZero(message = "La página actual debe ser 0 o mayor.")
    private int paginaActual;

    // Estado de lectura del libro (Ej: "Leyendo", "Terminado", "Pendiente", etc.)
    private String estadoLectura;

    // Puntuación que el usuario le da al libro (0 a 100)
    private int puntuacion;
}
