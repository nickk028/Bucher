package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class BibliotecaBasicDTO {
    // Id
    @Id
    private Long id;

    // Nombre de la biblioteca
    @NotBlank(message = "El nombre de la biblioteca es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre de la biblioteca debe tener entre 2 y 100 digitos.")
    private String nombre;
}