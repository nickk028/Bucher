package ar.edu.huergo.vectorial.calidad.bucher.entity.subscription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "suscripciones")
public class Suscripcion {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre de la suscripción
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre de la suscripción es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    // Porcentaje de cobertura de la suscripción (0 a 100)
    @Column(nullable = false)
    @Min(value = 0, message = "El porcentaje de cobertura no puede ser negativo.")
    @Max(value = 100, message = "El porcentaje de cobertura no puede ser mayor a 100.")
    private int porcentajeCobertura;

    // Límite mensual de libros que se pueden pedir prestados
    @Column(nullable = false)
    @PositiveOrZero(message = "El límite de libros debe ser 0 o mayor.")
    private int limiteLibros;
}