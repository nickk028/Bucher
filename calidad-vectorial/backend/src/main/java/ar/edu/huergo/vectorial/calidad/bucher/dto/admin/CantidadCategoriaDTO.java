package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class CantidadCategoriaDTO {

    // Nombre de la categoría
    private String categoria;

    // Cantidad de elementos de la categoría
    private long cantidad;
}