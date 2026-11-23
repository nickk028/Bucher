package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PanelPrestamosDTO {

    // Cantidad de préstamos totales
    private long prestamosTotales;

    // Cantidad de préstamos activos (con la publicación prestada)
    private long prestamosActivos;

    // Cantidad de préstamos incompletos (todavía sin devolución)
    private long prestamosIncompletos;

    // Porcentaje de préstamos activos sobre el total de préstamos (0 a 1)
    private double porcentajePrestamosActivos;

    // Cantidad de préstamos completados en cada mes del año actual
    private List<CantidadMesDTO> prestamosCompletadosPorMes = new ArrayList<>();

    // Cantidad de préstamos de cada categoría
    private List<CantidadCategoriaDTO> categoriasPrestamos = new ArrayList<>();
}