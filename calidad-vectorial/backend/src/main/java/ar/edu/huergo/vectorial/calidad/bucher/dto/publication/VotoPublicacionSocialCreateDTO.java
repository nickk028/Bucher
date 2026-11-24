package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.TipoVoto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class VotoPublicacionSocialCreateDTO {

    // Tipo de voto (Ej: "POSITIVO", "NEGATIVO")
    @NotNull(message = "El tipo de voto es obligatorio.")
    private TipoVoto tipo;
}