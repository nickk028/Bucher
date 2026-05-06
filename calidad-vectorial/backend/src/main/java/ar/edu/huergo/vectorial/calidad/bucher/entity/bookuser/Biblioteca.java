package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;

import java.util.ArrayList;
import java.util.List;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(exclude = {"librosUsuario"})
@Table(name = "bibliotecas")
public class Biblioteca {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre de la biblioteca
    @JoinColumn(name = "nombre", nullable = false, unique = false)
    @NotBlank(message = "El nombre de la biblioteca es obligatorio.")
    @NotNull(message = "El nombre de la biblioteca es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 2 y 100 digitos.")
    private String nombre;

    // Usuario Dueño de las bibliotecas
    // Relación muchos a 1 con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    // Lista de libros del usuario con sus datos agregados
    // Relación 1 a muchos con LibroUsuario
    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibroUsuario> librosUsuario = new ArrayList<>();
}