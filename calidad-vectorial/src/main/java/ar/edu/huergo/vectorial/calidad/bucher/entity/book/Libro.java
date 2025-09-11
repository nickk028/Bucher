package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "libros")
public class Libro {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Título del libro
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos.")
    private String titulo;

    // Descripción del libro
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "La descripción es obligatorio.")
    @Size(min = 2, max = 255, message = "La libro debe tener entre 2 y 255 digitos.")
    private String descripcion;

    // Cantidad de páginas del libro
    @Column(nullable = false)
    @Positive(message = "La cantidad de páginas debe ser mayor a 0.")
    private int paginas;

    // Edición del libro
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "La edicion es obligatoria.")
    @Size(min = 2, max = 100, message = "La edición debe tener entre 2 y 100 digitos.")
    private String edicion;

    // Calificación del libro
    @Column(nullable = false)
    @Positive(message = "La calificación debe ser mayor a 0.")
    @Min(0)
    @Max(100)
    private int calificacion;

    // Fecha de publicación del libro
    @Column(nullable = false)
    @NotNull(message = "La fecha de publicación es obligatoria.")
    private LocalDate fechaPublicacion;

    // URL de la foto del libro
    @Column(nullable = true)
    private String urlFoto;

    // Precio del libro
    @Column(nullable = false)
    @Positive(message = "El precio debe ser mayor a 0.")
    private double precio;

    // Categoría del libro
    @NotNull(message = "La categoria es obligatoria.")
    @NotEmpty(message = "La categoria es obligatoria.")
    @ElementCollection(targetClass = Categoria.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_categorias", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categoria;

    // Editorial del libro
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorial_id", nullable = false)
    @NotNull(message = "La editorial es obligatoria.")
    private Editorial editorial;

    // Autor del libro
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "El autor es obligatoria.")
    private Autor autor;

    //Reacion 1 a muchos con Pubicaciones
    @OneToMany(mappedBy = "libro")
    private List<Publicacion> publicaciones = new ArrayList<>();
}