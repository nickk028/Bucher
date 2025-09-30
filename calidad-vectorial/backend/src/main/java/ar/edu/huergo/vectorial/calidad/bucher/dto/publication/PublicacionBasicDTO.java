package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PublicacionBasicDTO {
    // Título del libro
    @NotBlank(message = "El libro es obligatorio.")
    @Size(min = 2, max = 100, message = "El libro debe tener entre 2 y 100 digitos.")
    private String titulo;

    // URL de la foto del libro
    String urlFoto;

    // Nickname del usuario
    String usuarioNickname;

    // Estado de la publicación (Ej: "Disponible", "Prestado", etc.)
    @NotBlank(message = "El estado de la publicación es obligatorio.")
    @Size(min = 2, max = 50, message = "El estado de la publicación debe tener entre 2 y 50 caracteres.")
    @Enumerated(EnumType.STRING)
    private Estado estadoPublicacion;

    // Límite de días para la devolucion del préstamo
    @Positive(message = "El límite de días debe ser mayor a 0.")
    @Min(1)
    @Max(365)
    private int limiteDias;
}
