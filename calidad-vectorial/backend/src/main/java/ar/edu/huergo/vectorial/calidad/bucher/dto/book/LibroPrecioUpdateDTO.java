package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroPrecioUpdateDTO {

    // Nuevo precio del libro
    @NotNull(message = "El precio es obligatorio.")
    @PositiveOrZero(message = "El precio no puede ser negativo.")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 enteros y 2 decimales.")
    private BigDecimal precio;
}