package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class GoogleLoginDTO {

    // Id Token entregado por Google Identity Services
    @NotBlank(message = "El idToken es obligatorio")
    private String idToken;
}