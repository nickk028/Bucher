package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionSocialDeleteDTO {

    // Razón de la eliminación (obligatoria cuando un admin elimina la publicación de otro usuario)
    @Size(max = 255, message = "La razón debe tener como máximo 255 caracteres.")
    private String razon;
}