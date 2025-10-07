package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RegistroPrestamoResponseDTO {
    // Fecha del registro de préstamo
    private LocalDate fechaPrestamo;

    // Fecha de devolución del préstamo
    private LocalDate fechaDevolucion;

    // Detalles de la publicación asociada al registro de préstamo
    private PublicacionResponseDTO publicacion;
}
