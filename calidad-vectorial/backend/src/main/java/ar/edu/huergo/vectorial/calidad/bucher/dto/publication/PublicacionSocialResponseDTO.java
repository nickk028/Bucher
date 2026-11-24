package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroSocialDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.deletion.RegistroEliminacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.TipoVoto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionSocialResponseDTO {

    // Id principal
    private Long id;

    // Usuario que creó la publicación (null si está eliminada y quien consulta no es admin)
    private UsuarioBasicDTO usuario;

    // Texto de la publicación (null si está eliminada y quien consulta no es admin)
    private String textoPublicacion;

    // Indica si la publicación contiene spoilers
    private boolean contieneSpoilers;

    // Fecha y hora de creación de la publicación
    private LocalDateTime fechaCreacion;

    // Hace cuánto se publicó (Ej: "Ahora", "5 Minutos", "2 Años")
    private String tiempoTranscurrido;

    // Libro referenciado (null si no tiene o si está eliminada y quien consulta no es admin)
    private LibroSocialDTO libro;

    // Nicknames de los usuarios etiquetados
    private List<String> etiquetas = new ArrayList<>();

    // Publicación a la que responde (null si no es una respuesta)
    private PublicacionSocialBasicDTO publicacionReferenciada;

    // Cantidad de respuestas directas
    private long cantidadRespuestas;

    // Cantidad de votos positivos
    private int votosPositivos;

    // Cantidad de votos negativos
    private int votosNegativos;

    // Voto del usuario que consulta (null si no votó)
    private TipoVoto votoUsuario;

    // Cantidad de reportes (solo se informa a los admins)
    private Integer reportes;

    // Indica si la publicación fue eliminada
    private boolean eliminada;

    // Registro de la eliminación (solo se informa a los admins)
    private RegistroEliminacionResponseDTO registroEliminacion;
}