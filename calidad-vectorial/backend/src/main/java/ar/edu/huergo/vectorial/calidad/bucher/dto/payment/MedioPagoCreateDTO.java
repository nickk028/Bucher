package ar.edu.huergo.vectorial.calidad.bucher.dto.payment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class MedioPagoCreateDTO {

    // Nombre del medio de pago
    @NotBlank(message = "El nombre del medio de pago es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    // Porcentaje de comisión del medio de pago (0 a 100)
    @Min(value = 0, message = "El porcentaje de comisión no puede ser negativo.")
    @Max(value = 100, message = "El porcentaje de comisión no puede ser mayor a 100.")
    private int porcentajeComision;
}