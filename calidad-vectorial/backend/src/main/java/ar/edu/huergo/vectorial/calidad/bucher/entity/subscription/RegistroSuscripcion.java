package ar.edu.huergo.vectorial.calidad.bucher.entity.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.Transaccion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(callSuper = true) // Genera equals y hashCode incluyendo los campos de la clase padre
@ToString(callSuper = true) // Genera toString incluyendo los campos de la clase padre
@Table(name = "registro_suscripciones")
public class RegistroSuscripcion extends Transaccion {

    // Relación muchos a 1 con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio.")
    @ToString.Exclude // Evita la recursión infinita en toString con Usuario
    private Usuario usuario;

    // Relación muchos a 1 con Suscripcion
    @ManyToOne
    @JoinColumn(name = "suscripcion_id", nullable = false)
    @NotNull(message = "La suscripción es obligatoria.")
    private Suscripcion suscripcion;

    // Fecha de inicio de la suscripción
    @Column(nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria.")
    private LocalDate fechaInicio;

    // Fecha de fin de la suscripción (null en el plan gratuito)
    @Column(nullable = true)
    private LocalDate fechaFin;

    // Precio base de la suscripción sin impuestos al momento del pago
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "El precio base es obligatorio.")
    @PositiveOrZero(message = "El precio base no puede ser negativo.")
    private BigDecimal precioBase;

    // Monto final pagado por el usuario con impuestos
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "El monto pagado es obligatorio.")
    @PositiveOrZero(message = "El monto pagado no puede ser negativo.")
    private BigDecimal montoPagado;
}