package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class TablaEstadisticaUsuarioDTO {

    // Id principal
    private Long id;

    // Nickname del usuario
    private String nickname;

    // Nombre de la cuenta del usuario (su correo)
    private String username;

    // Cantidad de publicaciones del usuario
    private long cantPublicaciones;

    // Cantidad de posteos del usuario (incluidos los eliminados)
    private long cantPosteos;

    // Cantidad de denuncias recibidas en sus posteos (incluidos los eliminados)
    private long cantDenuncias;
}