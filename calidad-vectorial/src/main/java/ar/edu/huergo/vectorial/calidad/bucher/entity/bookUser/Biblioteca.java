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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bibliotecas")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1 a 1 con Usuario
    @OneToOne
    @JoinTable(
        name = "bibliotecas_usuario",
        joinColumns = @JoinColumn(name = "bibliotecas_id"),
        inverseJoinColumns = @JoinColumn(name = "usuarios_id")
    )
    private Usuario usuario;

    // Relación 1 a muchos con LibroUsuario 
    @OneToMany(mappedBy = "biblioteca")
    @JoinTable(
        name = "bibliotecas_libro_usuario",
        joinColumns = @JoinColumn(name = "bibliotecas_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_usuario_id")
    )
    private List<LibroUsuario> librosUsuario;
}
