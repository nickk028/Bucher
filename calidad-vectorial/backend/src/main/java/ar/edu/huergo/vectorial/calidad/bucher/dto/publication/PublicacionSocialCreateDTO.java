package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionSocialCreateDTO {

    // Texto de la publicación
    @NotBlank(message = "El texto de la publicación es obligatorio.")
    @Size(
        min = 1,
        max = 500,
        message = "El texto de la publicación debe tener entre 1 y 500 caracteres."
    )
    private String textoPublicacion;

    // Indica si la publicación contiene spoilers (si no se envía se asume false)
    private boolean contieneSpoilers;

    // Id del libro referenciado (opcional, no se permite en respuestas)
    private Long libroId;

    // Id de la publicación a la que responde (opcional)
    private Long publicacionReferenciadaId;

    // Nicknames de los usuarios etiquetados
    @Size(max = 10, message = "Se pueden etiquetar como máximo 10 usuarios.")
    private List<
        @NotBlank(message = "El nickname de la etiqueta es obligatorio.")
        @Size(max = 32, message = "El nickname debe tener como máximo 32 caracteres.") String
    > etiquetas = new ArrayList<>();
}