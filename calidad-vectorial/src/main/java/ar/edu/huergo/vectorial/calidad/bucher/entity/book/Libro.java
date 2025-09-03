package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos.")
    private String titulo;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "La descripción es obligatorio.")
    @Size(min = 2, max = 255, message = "La libro debe tener entre 2 y 255 digitos.")
    private String descripcion;

    @Column(nullable = false)
    @NotBlank(message = "Las páginas es obligatorio.")
    @Positive(message = "La cantidad de páginas debe ser mayor a 0.")
    private int paginas;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "La edicion es obligatoria.")
    @Size(min = 2, max = 100, message = "La edición debe tener entre 2 y 100 digitos.")
    private String edicion;

    @Column(nullable = false)
    @NotBlank(message = "La calificación es obligatorio.")
    @Positive(message = "La calificación debe ser mayor a 0.")
    @Min(0)
    @Max(100)
    private int calificacion;

    @Column(nullable = false)
    @NotBlank(message = "La fecha de publicación es obligatoria.")
    private LocalDate fechaPublicacion;

    @Column(nullable = true)
    private String urlFoto;

    @Column(nullable = false)
    @NotBlank(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a 0.")
    private double precio;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatorio.")
    @NotEmpty(message = "El precio es obligatorio.")
    @Size(min = 2, max = 100, message = "La categoría debe tener entre 2 y 100 digitos.")
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categoria;

    @NotBlank(message = "La editorial es obligatoria.")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "libros_editoriales",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "editorial_id")
    )
    private Editorial editorial;

    @NotBlank(message = "El autor es obligatoria.")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "libros_autores",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Autor autor;
}