package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
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

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El usuario creador es obligatorio.")
    @Size(min = 2, max = 100, message = "El usuario creador debe tener entre 2 y 100 caracteres.")
    private String usuarioCreador;

    @Column(nullable = false)
    @NotNull(message = "La fecha de creación es obligatoria.")
    private LocalDate fechaCreacion;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres.")
    private String descripcion;

    @Column(nullable = false)
    @Positive(message = "El límite de días debe ser mayor a 0.")
    @Min(1)
    @Max(365)
    private int limiteDias;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Los detalles del estado del libro son obligatorios.")
    @Size(min = 2, max = 100, message = "Los detalles deben tener entre 2 y 100 caracteres.")
    private String detallesEstadoLibro;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado de la publicación es obligatorio.")
    @Size(min = 2, max = 50, message = "El estado de la publicación debe tener entre 2 y 50 caracteres.")
    @Enumerated(EnumType.STRING)
    private Estado estadoPublicacion;

    @OneToOne
    @JoinTable(name = "publicaciones_libros",
    joinColumns = @JoinColumn(name = "publicacion_id"),
    inverseJoinColumns = @JoinColumn(name = "libro_id"))
    private Libro libro;
}