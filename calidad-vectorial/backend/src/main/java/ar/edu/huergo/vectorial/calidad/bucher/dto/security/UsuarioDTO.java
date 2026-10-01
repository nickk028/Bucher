package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class UsuarioDTO {

    // Atributos obligatorios
    String username;
    Set<Rol> roles;
}
