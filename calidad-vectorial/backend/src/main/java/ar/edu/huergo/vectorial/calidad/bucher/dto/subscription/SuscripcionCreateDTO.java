package ar.edu.huergo.vectorial.calidad.bucher.dto.subscription;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class SuscripcionCreateDTO {

    // Nombre de la suscripción
    @NotBlank(message = "El nombre de la suscripción es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    // Porcentaje de cobertura de la suscripción (0 a 100)
    @Min(value = 0, message = "El porcentaje de cobertura no puede ser negativo.")
    @Max(value = 100, message = "El porcentaje de cobertura no puede ser mayor a 100.")
    private int porcentajeCobertura;

    // Límite mensual de libros que se pueden pedir prestados
    @PositiveOrZero(message = "El límite de libros debe ser 0 o mayor.")
    private int limiteLibros;

    // Precio inicial de la suscripción sin impuestos
    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a 0.")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 enteros y 2 decimales.")
    private BigDecimal precio;
}