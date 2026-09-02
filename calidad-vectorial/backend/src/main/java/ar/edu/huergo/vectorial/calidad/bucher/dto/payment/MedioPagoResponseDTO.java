package ar.edu.huergo.vectorial.calidad.bucher.dto.payment;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class MedioPagoResponseDTO {

    // Id
    @Id
    private Long id;

    // Nombre del medio de pago
    private String nombre;

    // Porcentaje de comisión del medio de pago (0 a 100)
    private int porcentajeComision;
}