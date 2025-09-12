package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class BibliotecaResponseDTO {
    // List de todos los libros del usuario
    private List<LibroUsuarioResponseDTO> librosUsuarioResponseDTO;
}