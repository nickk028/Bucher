package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class DataCantidadesResponseDTO {
    long cantLibros;
    long cantPrestamos;
    long cantPublicacionesSociales;
}