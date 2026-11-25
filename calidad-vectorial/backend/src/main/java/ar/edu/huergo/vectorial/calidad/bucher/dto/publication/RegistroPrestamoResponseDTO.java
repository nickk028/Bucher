package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RegistroPrestamoResponseDTO {

    // Id del regisro de préstamo
    private Long id;

    // Fecha del registro de préstamo
    private LocalDate fechaPrestamo;

    // Fecha de devolución del préstamo
    private LocalDate fechaDevolucion;

    // Detalles de la publicación asociada al registro de préstamo
    private PublicacionResponseDTO publicacion;

    private String usuarioPrestamista;

    private String avatarUsuarioPrestamista;

    private String nickanmeUsuarioPrestamista;

    private String confirmacionTitulo;

    private String confirmacionAutor;

    private String confirmacionDetallesEstado;

    private List<String> confirmacionDetallesEstadoLibro = new ArrayList<>();

    private String comentariosAdicionales;

    private String estaEnCondiciones;

    private String motivoNoEnCondiciones;
}
