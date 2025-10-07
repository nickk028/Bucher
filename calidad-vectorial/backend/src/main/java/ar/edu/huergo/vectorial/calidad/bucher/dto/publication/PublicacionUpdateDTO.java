package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PublicacionUpdateDTO {
    // Id
    @Id
    private Long id;

    // Descripción de la publicación
    @Size(min = 5, max = 255)
    private String descripcion;

    // Límite de días para prestar el libro
    @Min(0)
    @Max(365)
    private int limiteDias;

    // Detalles del estado del libro (Ej: "Nuevo", "Usado - Buen estado", "Dañado", etc.)
    @Size(min = 2, max = 100, message = "Los detalles deben tener entre 2 y 100 caracteres.")
    private String detallesEstadoLibro;

    // Estado de la publicación (Ej: "Disponible", "Prestado", etc.)
    private Estado estadoPublicacion;
}