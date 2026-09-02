package ar.edu.huergo.vectorial.calidad.bucher.entity.payment;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Inheritance(strategy = InheritanceType.JOINED) // Cada subclase concreta tendrá su propia tabla enlazada por Id
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "transacciones")
public abstract class Transaccion {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Medio de pago utilizado en la transacción
    // Relación muchos a 1 con MedioPago
    @ManyToOne
    @JoinColumn(name = "medio_pago_id", nullable = false)
    @NotNull(message = "El medio de pago es obligatorio.")
    private MedioPago medioPago;

    // Monto acreditado de la transacción
    @Column(nullable = false)
    @PositiveOrZero(message = "El monto acreditado debe ser 0 o mayor.")
    private double montoAcreditado;

    // Fecha y hora de la transacción
    @Column(nullable = false)
    @CurrentTimestamp
    private LocalDateTime fechaTransaccion;
}