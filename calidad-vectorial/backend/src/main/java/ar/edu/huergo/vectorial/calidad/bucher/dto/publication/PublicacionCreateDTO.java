package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(callSuper = true)
public class PublicacionCreateDTO extends PublicacionDTO {
    private List<String> detallesAdicionales = new ArrayList<>();

    @NotBlank(message = "Los detalles es obligatoria.")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres.")
    private String detallesEstadoLibro;
}
