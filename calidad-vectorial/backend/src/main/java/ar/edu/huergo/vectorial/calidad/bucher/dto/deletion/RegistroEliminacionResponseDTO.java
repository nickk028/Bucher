package ar.edu.huergo.vectorial.calidad.bucher.dto.deletion;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioBasicDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RegistroEliminacionResponseDTO {

    // Fecha y hora en la que se eliminó
    private LocalDateTime fechaEliminacion;

    // Razón de la eliminación
    private String razon;

    // Usuario que realizó la eliminación
    private UsuarioBasicDTO usuarioEliminador;
}