package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroPrecioUpdateDTO {

    // Nuevo precio del libro
    @Positive(message = "El precio debe ser mayor a 0.")
    private double precio;
}