package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class LibroUsuarioUpdateDTO {
    // Pagina actual de libro que está leyendo el usuario
    @PositiveOrZero(message = "La página actual debe ser 0 o mayor.")
    private int paginaActual;

    // Estado de lectura del libro (Ej: "Leyendo", "Terminado", "Pendiente", etc.)
    @Size(min = 2, max = 100, message = "El estado de lectura debe tener entre 2 y 100 digitos.")
    private String estadoLectura;

    // Puntuación que el usuario le da al libro (0 a 100)
    @Min(value = 0, message = "La puntuacion no pueden ser negativas.")
    @Max(value = 100, message = "La puntuacion maxima es 100.")
    private int puntuacion;
}