package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import java.time.LocalDate;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El username es obligatorio.")
    @Email(message = "El nombre debe ser un mail con un formato válido.")
    private String usuarioCreador;

    // Fecha de creación de la publicación
    @NotNull(message = "La fecha de creación es obligatoria.")
    private LocalDate fechaCreacion;

    // Estado de la publicación (Ej: "Disponible", "Prestado", etc.)
    @NotBlank(message = "El estado de la publicación es obligatorio.")
    @Size(min = 2, max = 50, message = "El estado de la publicación debe tener entre 2 y 50 caracteres.")
    @Enumerated(EnumType.STRING)
    private Estado estadoPublicacion;

    // Descripción de la publicación
    @Column(nullable = false, length = 255)
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres.")
    private String descripcion;

    // Desripción del usuario
    private String descripcionUsuario;

    //Nombre del autor
    private String nombre;

    // Url de la foto del libro
    private String urlFoto;
}