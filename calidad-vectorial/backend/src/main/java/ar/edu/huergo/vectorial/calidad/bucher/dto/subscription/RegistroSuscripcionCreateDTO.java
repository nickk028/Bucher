package ar.edu.huergo.vectorial.calidad.bucher.dto.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RegistroSuscripcionCreateDTO {

    // Id de la suscripción a contratar
    @NotNull(message = "La suscripción es obligatoria.")
    private Long suscripcionId;

    // Id del medio de pago
    @NotNull(message = "El medio de pago es obligatorio.")
    private Long medioPagoId;

    // Número de la tarjeta
    @NotBlank(message = "El número de tarjeta es obligatorio.")
    @Size(max = 25, message = "El número de tarjeta debe tener como máximo 25 caracteres.")
    @ToString.Exclude // Evita exponer el número de tarjeta en toString
    private String numeroTarjeta;

    // Vencimiento de la tarjeta (Ej: "12/30")
    @NotBlank(message = "El vencimiento es obligatorio.")
    @Size(max = 7, message = "El vencimiento debe tener como máximo 7 caracteres.")
    private String vencimiento;

    // Código de seguridad de la tarjeta
    @NotBlank(message = "El código de seguridad es obligatorio.")
    @Size(max = 4, message = "El código de seguridad debe tener como máximo 4 caracteres.")
    @ToString.Exclude // Evita exponer el código de seguridad en toString
    private String cvv;

    // Titular de la tarjeta
    @NotBlank(message = "El titular es obligatorio.")
    @Size(max = 100, message = "El titular debe tener como máximo 100 caracteres.")
    private String titular;
}