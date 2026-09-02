package ar.edu.huergo.vectorial.calidad.bucher.entity.payment;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(exclude = {"transacciones"})
@Table(name = "medios_pago")
public class MedioPago {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre del medio de pago
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre del medio de pago es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    // Porcentaje de comisión del medio de pago (0 a 100)
    @Column(nullable = false)
    @Min(value = 0, message = "El porcentaje de comisión no puede ser negativo.")
    @Max(value = 100, message = "El porcentaje de comisión no puede ser mayor a 100.")
    private int porcentajeComision;

    // Relación 1 a muchos con Transaccion
    @OneToMany(mappedBy = "medioPago")
    private List<Transaccion> transacciones = new ArrayList<>();
}