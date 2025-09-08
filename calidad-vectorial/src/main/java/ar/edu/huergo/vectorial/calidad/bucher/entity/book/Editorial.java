package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "editoriales")
public class Editorial {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre de la editorial
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos")
    private String nombre;

    // URL de la pagina de Wikipedia de la editorial
    @Column(nullable = true)
    private String urlWikipedia;

    // Relacion 1 a muchos con Libros
    @OneToMany(mappedBy = "editorial")
    private List<Libro> libros = new ArrayList<>();

    // Constructor
    public Editorial(String nombre, String urlWikipedia) {
        this.nombre = nombre;
        this.urlWikipedia = urlWikipedia;
    }
}