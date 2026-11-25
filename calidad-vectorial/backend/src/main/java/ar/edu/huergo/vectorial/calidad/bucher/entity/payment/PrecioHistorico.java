package ar.edu.huergo.vectorial.calidad.bucher.entity.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

@Entity // Marca la clase como una entidad de JPA
@Inheritance(strategy = InheritanceType.JOINED) // Cada subclase concreta tendrá su propia tabla enlazada por Id
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "precio_historico")
public abstract class PrecioHistorico {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Precio registrado
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "El precio es obligatorio.")
    @PositiveOrZero(message = "El precio no puede ser negativo.")
    private BigDecimal precio;

    // Fecha en la que se modificó el precio
    @Column(nullable = false)
    @CurrentTimestamp
    private LocalDate fechaModificacion;
}