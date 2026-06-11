package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;

import org.hibernate.annotations.CurrentTimestamp;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "publicaciones")
public class PublicacionSocial {

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
    @CurrentTimestamp
    private LocalDate fechaCreacion;

    // Descripción de la publicación
    @Column(nullable = false, length = 128)
    @Size(min = 5, max = 128, message = "La descripción debe tener entre 5 y 128 caracteres.")
    private String descripcion;

    // Texto de la publicación
    @Column(nullable = false, length = 255)
    @NotBlank(message = "El texto de la publicación es obligatorio.")
    @Size(min = 5, max = 255, message = "El texto de la publicación debe tener entre 5 y 255 caracteres.")
    private String textoPublicacion;

    // Relación muchos a 1 con Libro
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = true)
    private Libro libro;
}