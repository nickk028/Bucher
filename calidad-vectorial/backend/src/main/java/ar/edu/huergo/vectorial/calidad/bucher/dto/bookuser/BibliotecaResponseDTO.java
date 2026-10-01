package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class BibliotecaResponseDTO {

    // Id
    @Id
    private Long id;

    // Nombre de la biblioteca
    private String nombre;

    // Lista de librosUsuario de la biblioteca
    private List<LibroUsuarioResponseDTO> librosUsuario;
}
