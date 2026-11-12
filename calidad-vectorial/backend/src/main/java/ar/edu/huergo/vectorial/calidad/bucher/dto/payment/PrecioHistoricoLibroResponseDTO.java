package ar.edu.huergo.vectorial.calidad.bucher.dto.payment;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PrecioHistoricoLibroResponseDTO {

    // Precio registrado
    private double precio;

    // Fecha en la que se modificó el precio
    private LocalDate fechaModificacion;
}
