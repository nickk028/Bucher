package ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroUsuarioResponseDTO {
    // Id
    @Id
    private Long id;
    
    // Título del libro
    private String titulo;

    // Autor del libro
    private String autor;

    // Pagina actual de libro que está leyendo el usuario
    private int paginaActual;

    // Estado de lectura del libro (Ej: "Leyendo", "Terminado", "Pendiente", etc.)
    private EstadoLectura estadoLectura;

    // Puntuación que el usuario le da al libro (0 a 100)
    private int puntuacion;

    // Foto del libro
    private String urlFoto;
}