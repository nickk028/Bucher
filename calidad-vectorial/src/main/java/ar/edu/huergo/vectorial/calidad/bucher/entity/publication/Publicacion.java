package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;

import org.hibernate.annotations.CurrentTimestamp;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "publicaciones")
public class Publicacion {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Usuario que creó la publicación
    // Relacion 1 a Muchos con la tabla usuario
    @ManyToOne
    @NotNull(message = "El usuario es obligatorio.")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Fecha de creación de la publicación
    @Column(nullable = false)
    @NotNull(message = "La fecha de creación es obligatoria.")
    @CurrentTimestamp
    private LocalDate fechaCreacion;

    // Descripción de la publicación
    @Column(nullable = false, length = 255)
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres.")
    private String descripcion;

    // Límite de días para la devolucion del préstamo
    @Column(nullable = false)
    @NotNull(message = "El mímite de días es obligatorio.")
    @Positive(message = "El límite de días debe ser mayor a 0.")
    @Min(1)
    @Max(365)
    private int limiteDias;

    // Detalles del estado del libro (Ej: "Nuevo", "Usado - Buen estado", "Dañado", etc.)
    @Column(nullable = false, length = 100)
    @Size(min = 2, max = 100, message = "Los detalles deben tener entre 2 y 100 caracteres.")
    private String detallesEstadoLibro;

    // Estado de la publicación (Ej: "Disponible", "Prestado", etc.)
    @Column(nullable = false, length = 50)
    @NotNull(message = "El estado de la publicación es obligatorio.")
    @Enumerated(EnumType.STRING)
    private Estado estadoPublicacion;

    // Relación 1 a 1 con Libro
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    @NotNull(message = "El libro es obligatorio.")
    private Libro libro;
}