package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PrestamoControlDTO {

    private Long id;

    private String confirmacionTitulo;

    private String confirmacionAutor;

    private String confirmacionDetallesEstado;

    private List<String> confirmacionDetallesEstadoLibro = new ArrayList<>();

    private String comentariosAdicionales;

    private String estaEnCondiciones;

    private String motivoNoEnCondiciones;
}
