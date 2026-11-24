package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PorcentajeCategoriaDTO {

    // Nombre de la categoría
    private String categoria;

    // Porcentaje de la categoría sobre el total (0 a 1)
    private double porcentaje;
}