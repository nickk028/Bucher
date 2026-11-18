package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PanelPosteosDTO {

    // Cantidad de posteos publicados (incluidos los eliminados)
    private long posteosPublicados;

    // Cantidad de posteos que responden a otros posteos
    private long posteosConReferencias;

    // Cantidad de posteos denunciados (no eliminados)
    private long posteosDenunciados;

    // Porcentaje de posteos denunciados sobre el total de posteos (0 a 1)
    private double porcentajePosteosDenunciados;

    // Cantidad de posteos eliminados
    private long posteosEliminados;

    // Porcentaje de posteos eliminados sobre el total de posteos (0 a 1)
    private double porcentajePosteosEliminados;

    // Porcentaje de posteos que tienen un libro sobre el total de posteos (0 a 1)
    private double porcentajePosteosConMenciones;

    // Cantidad de posteos de cada mes del año actual
    private List<CantidadMesDTO> posteosPorMes = new ArrayList<>();

    // Porcentaje de las 5 categorías más mencionadas y de las restantes agrupadas en "Otras"
    private List<PorcentajeCategoriaDTO> categoriasMasMencionadas = new ArrayList<>();

    // Los 5 libros más mencionados
    private List<RankingLibroDTO> librosMasMencionados = new ArrayList<>();
}