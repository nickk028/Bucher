package ar.edu.huergo.vectorial.calidad.bucher.entity.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "precio_historico_suscripcion")
public class PrecioHistoricoSuscripcion extends PrecioHistorico {

    // Relación muchos a 1 con Suscripcion
    @ManyToOne
    @JoinColumn(name = "suscripcion_id", nullable = false)
    @NotNull(message = "La suscripción es obligatoria.")
    @ToString.Exclude // Evita la recursión infinita en toString con Suscripcion
    private Suscripcion suscripcion;
}