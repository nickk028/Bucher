package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class TablaEstadisticaRegistroPrestamoDTO {

    // Id principal del registro de préstamo
    private Long id;

    // Username del prestamista (creador de la publicación)
    private String nickname;

    // Título del libro prestado
    private String tituloLibro;

    // Estado del préstamo
    private String estado;

    // Fecha de entrega del libro
    private LocalDate fechaEntrega;

    // Fecha de devolución del libro
    private LocalDate fechaDevolucion;
}