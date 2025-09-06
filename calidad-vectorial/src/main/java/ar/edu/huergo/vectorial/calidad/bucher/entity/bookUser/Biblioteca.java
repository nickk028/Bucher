package ar.edu.huergo.vectorial.calidad.bucher.entity.bookUser;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "bibliotecas")
public class Biblioteca {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Usuario Dueño de la biblioteca 
    // Relación 1 a 1 con Usuario
    @OneToOne
    @JoinTable(
        name = "bibliotecas_usuario",
        joinColumns = @JoinColumn(name = "bibliotecas_id"),
        inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    private Usuario usuario;

    // Lista de libros del usuario con sus datos agregados
    // Relación 1 a muchos con LibroUsuario 
    @OneToMany(mappedBy = "biblioteca")
    @JoinTable(
        name = "bibliotecas_libro_usuario",
        joinColumns = @JoinColumn(name = "bibliotecas_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_usuario_id")
    )
    private List<LibroUsuario> librosUsuario;
}
