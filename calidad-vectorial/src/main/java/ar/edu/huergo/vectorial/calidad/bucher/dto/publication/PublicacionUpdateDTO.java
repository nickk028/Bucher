package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionUpdateDTO {

    // Descripción de la publicación
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 5, max = 255)
    private String descripcion;

    // Límite de días para prestar el libro
    @Positive(message = "El límite de días debe ser mayor a 0.")
    @Min(1)
    @Max(365)
    private int limiteDias;
}