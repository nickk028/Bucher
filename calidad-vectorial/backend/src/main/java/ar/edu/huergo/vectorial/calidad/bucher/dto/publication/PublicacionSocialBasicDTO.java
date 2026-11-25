package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioBasicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionSocialBasicDTO {

    // Id principal
    private Long id;

    // Usuario que creó la publicación (null si está eliminada y quien consulta no es admin)
    private UsuarioBasicDTO usuario;

    // Texto de la publicación (null si está eliminada y quien consulta no es admin)
    private String textoPublicacion;

    // Indica si la publicación contiene spoilers
    private boolean contieneSpoilers;

    // Hace cuánto se publicó (Ej: "Ahora", "5 Minutos", "2 Años")
    private String tiempoTranscurrido;

    // Indica si la publicación fue eliminada
    private boolean eliminada;
}