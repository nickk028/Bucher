package ar.edu.huergo.vectorial.calidad.bucher.util;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase utilitaria para las estadísticas del panel de administración.
 * Provee métodos para calcular porcentajes y para armar los rankings de categorías.
 */
public class EstadisticasUtil {

    // Cantidad de elementos que se muestran en los rankings
    public static final int CANTIDAD_DESTACADOS = 5;

    // Nombre del grupo que reúne las categorías que no están entre las destacadas
    private static final String NOMBRE_OTRAS = "Otras";

    /**
     * Calcula el porcentaje de una parte sobre un total como valor entre 0 y 1, con 3 decimales.
     * @param parte la cantidad que se quiere medir
     * @param total la cantidad total
     * @return un valor pasado a long de tres decimales
     */
    public static double calcularPorcentaje(long parte, long total) {
        if (total == 0) {
            return 0.0;
        }
        return Math.round((double) parte / total * 1000.0) / 1000.0;
    }

    /**
     * Calcula el porcentaje de las 5 categorías con más cantidad y de las restantes agrupadas en "Otras".
     * El porcentaje se calcula sobre la suma de todas las cantidades.
     * @param cantidades El mapa con cada categoría y su cantidad
     * @return Mapa ordenado de mayor a menor con el nombre de la categoría y su porcentaje
     */
    public static Map<String, Double> calcularCategoriasDestacadas(
        Map<Categoria, Long> cantidades
    ) {
        long total = cantidades.values().stream().mapToLong(Long::longValue).sum();

        List<Map.Entry<Categoria, Long>> destacadas = cantidades
            .entrySet()
            .stream()
            .sorted(Map.Entry.<Categoria, Long>comparingByValue().reversed())
            .limit(CANTIDAD_DESTACADOS)
            .toList();

        Map<String, Double> porcentajes = new LinkedHashMap<>();
        long sumaDestacadas = 0;
        for (Map.Entry<Categoria, Long> destacada : destacadas) {
            porcentajes.put(
                destacada.getKey().getNombre(),
                calcularPorcentaje(destacada.getValue(), total)
            );
            sumaDestacadas += destacada.getValue();
        }
        porcentajes.put(NOMBRE_OTRAS, calcularPorcentaje(total - sumaDestacadas, total));

        return porcentajes;
    }
}