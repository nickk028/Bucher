package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.time.LocalDate;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionSocialResponseDTO {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Usuario que creó la publicación
    private Usuario usuario;

    // Fecha de creación de la publicación
    private LocalDate fechaCreacion;

    // Descripción de la publicación
    private String descripcion;

    // Texto de la publicación
    private String textoPublicacion;

    // Relación muchos a 1 con Libro
    private Libro libro;
}