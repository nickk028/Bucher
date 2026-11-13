package ar.edu.huergo.vectorial.calidad.bucher.dto.subscription;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class SuscripcionResponseDTO {

    // Id principal
    private Long id;

    // Nombre de la suscripción
    private String nombre;

    // Porcentaje de cobertura de la suscripción (0 a 100)
    private int porcentajeCobertura;

    // Límite mensual de libros que se pueden pedir prestados
    private int limiteLibros;

    // Precio actual de la suscripción sin impuestos (último precio histórico, null si no tiene)
    private BigDecimal precio;
}