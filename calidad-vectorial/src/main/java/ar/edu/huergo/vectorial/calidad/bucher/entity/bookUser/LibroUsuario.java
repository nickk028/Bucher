package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "libro_usuario")
public class LibroUsuario {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Pagina actual de libro que está leyendo el usuario
    @Column(nullable = false) 
    @NotNull(message = "La página actual es obligatoria.")
    @PositiveOrZero(message = "La página actual debe ser 0 o mayor.")
    private int paginaActual;

    // Estado de lectura del libro (Ej: "Leyendo", "Terminado", "Pendiente", etc.)
    @Column(nullable = false, length = 50)
    @NotNull(message = "El estado es obligatorio.")
    private String estadoLectura;

    // Puntuación que el usuario le da al libro (0 a 100)
    @Column(nullable = false)
    @Min(value = 0, message = "La puntuacion no pueden ser negativas.")
    @Max(value = 100, message = "La puntuacion maxima es 100.")
    private int puntuacion;

    // Relación muchos a uno con Libro
    @ManyToOne
    @JoinTable(
        name = "libro_usuario_libro",
        joinColumns = @JoinColumn(name = "libro_usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "libros_id")
    )
    private Libro libro;

    // Relación muchos a uno con Usuario
    @ManyToOne
    @JoinTable(
        name = "libro_usuario_usuario",
        joinColumns = @JoinColumn(name = "libro_usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    private Usuario usuario;
}
