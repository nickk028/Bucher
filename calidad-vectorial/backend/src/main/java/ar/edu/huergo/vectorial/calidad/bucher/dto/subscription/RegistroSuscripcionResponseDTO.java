package ar.edu.huergo.vectorial.calidad.bucher.dto.subscription;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RegistroSuscripcionResponseDTO {

    // Id del registro de suscripción
    private Long id;

    // Username del usuario dueño del registro
    private String usuario;

    // Suscripción del registro
    private SuscripcionResponseDTO suscripcion;

    // Nombre del medio de pago utilizado
    private String medioPago;

    // Fecha de inicio de la suscripción
    private LocalDate fechaInicio;

    // Fecha de fin de la suscripción (null en el plan gratuito)
    private LocalDate fechaFin;

    // Precio base sin impuestos al momento del pago
    private BigDecimal precioBase;

    // Monto final pagado con impuestos
    private BigDecimal montoPagado;

    // Monto acreditado a la empresa luego de la comisión del medio de pago
    private BigDecimal montoAcreditado;

    // Fecha y hora de la transacción
    private LocalDateTime fechaTransaccion;
}