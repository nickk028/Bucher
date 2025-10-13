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
@Table(name = "autores")
public class Autor {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre del autor
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 digitos")
    private String nombre;

    // Descripción del autor
    @Column(nullable = true)
    @Size(max = 255, message = "La descripcion debe tener como máximo 100 digitos")
    private String descripcion;

    // URL de la pagina de Wikipedia del autor
    @Column(nullable = true)
    private String urlWikipedia;

    // URL de la imagen del autor
    @Column(nullable = true)
    private String urlFotoAutor;

    // Relacion 1 a Muchos con Libros
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros = new ArrayList<>();

    // Constructor
    public Autor(String nombre, String urlWikipedia) {
        this.nombre = nombre;
        this.urlWikipedia = urlWikipedia;
    }

    public Autor(String nombre, String descripcion, String urlWikipedia, String urlFotoAutor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlWikipedia = urlWikipedia;
        this.urlFotoAutor = urlFotoAutor;
    }
}