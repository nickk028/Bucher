package ar.edu.huergo.vectorial.calidad.bucher.entity.bookUser;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libro_usuario")
public class LibroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @PositiveOrZero(message = "La página actual debe ser 0 o mayor.")
    private int paginaActual;

    @Column(nullable = false, length = 50)
    @NotNull(message = "El estado es obligatorio.")
    private String estadoLectura;

    @Column(nullable = false)
    @Min(value = 0, message = "La puntuacion no pueden ser negativas.")
    @Max(value = 100, message = "La puntuacion maxima es 100.")
    private int puntuacion;

    @ManyToOne
    @JoinTable(
        name = "libro_usuario_libro",
        joinColumns = @JoinColumn(name = "libro_usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "libros_id")
    )
    private Libro libro;

    @ManyToOne
    @JoinTable(
        name = "libro_usuario_usuario",
        joinColumns = @JoinColumn(name = "libro_usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    private Usuario usuario;
}
