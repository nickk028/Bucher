package ar.edu.huergo.vectorial.calidad.bucher.dto.publication;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionUpdateDTO {
    @NotBlank(message = "La descripción es obligatoria.")
    @Size(min = 5, max = 255)
    private String descripcion;

    @Positive(message = "El límite de días debe ser mayor a 0.")
    @Min(1)
    @Max(365)
    private int limiteDias;
}