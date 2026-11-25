package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class UsuarioBasicDTO {

    // Id principal
    private Long id;

    // Nombre de la cuenta del usuario
    private String username;

    // Nickname del usuario
    private String nickname;

    // Url del avatar del usuario
    private String avatar;
}