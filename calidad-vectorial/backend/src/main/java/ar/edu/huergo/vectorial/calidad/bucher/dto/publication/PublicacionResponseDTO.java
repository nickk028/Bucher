package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.time.LocalDate;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(callSuper = true)
public class PublicacionResponseDTO extends PublicacionDTO {
    // Id
    @Id
    private Long id;
    
    // Nombre del usuario
    private String usuarioCreador;

    // Fecha de creación de la publicación
    private LocalDate fechaCreacion;

    // Estado de la publicación (Ej: "Disponible", "Prestado", etc.)
    @Enumerated(EnumType.STRING)
    private Estado estadoPublicacion;

    // Descripción de la publicación
    private String descripcion;

    // Desripción del usuario
    private String descripcionUsuario;

    //Nombre del autor
    private String nombre;

    // Url de la foto del libro
    private String urlFoto;
}