package ar.edu.huergo.vectorial.calidad.bucher.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LibroBasicDTO {

    // Id principal
    private Long id;

    // Titulo del libro
    private String titulo;

    // URL de la foto del libro
    private String urlFoto;
}
