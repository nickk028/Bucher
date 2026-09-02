package ar.edu.huergo.vectorial.calidad.bucher.entity.payment;

import java.time.LocalDate;

import org.hibernate.annotations.CurrentTimestamp;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "precios_historicos")
public class PrecioHistorico {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Precio del libro en el momento del registro
    @Column(nullable = false)
    @Positive(message = "El precio debe ser mayor a 0.")
    private double precio;

    // Fecha en la que se modificó el precio
    @Column(nullable = false)
    @CurrentTimestamp
    private LocalDate fechaModificacion;

    // Relación muchos a 1 con Libro
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    @NotNull(message = "El libro es obligatorio.")
    private Libro libro;
}