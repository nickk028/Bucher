package ar.edu.huergo.vectorial.calidad.bucher.dto.subscription;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class SuscripcionPrecioUpdateDTO {

    // Nuevo precio de la suscripción sin impuestos
    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a 0.")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 enteros y 2 decimales.")
    private BigDecimal precio;
}