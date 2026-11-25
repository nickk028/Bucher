package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class TablaEstadisticaPublicacionSocialDTO {

    // Id principal
    private Long id;

    // Nickname del usuario que creó la publicación
    private String nickname;

    // Texto completo de la publicación
    private String textoPublicacion;

    // Cantidad de denuncias de la publicación
    private int cantDenuncias;

    // Fecha y hora de creación de la publicación
    private LocalDateTime fechaCreacion;

    // Indica si la publicación fue eliminada
    private boolean eliminada;

    // Indica si la publicación es una respuesta a otra publicación
    private boolean respuesta;
}