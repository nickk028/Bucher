package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class RankingLibroDTO {

    // Id del libro
    private Long id;

    // Título del libro
    private String titulo;

    // Cantidad que ubica al libro en el ranking
    private long cantidad;
}