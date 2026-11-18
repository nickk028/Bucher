package ar.edu.huergo.vectorial.calidad.bucher.util;

/**
 * Clase utilitaria para el cálculo de porcentajes.
 * Provee métodos para calcular la proporción de una parte sobre un total.
 */
public class PorcentajeUtil {

    /**
     * Calcula el porcentaje de una parte sobre un total como valor entre 0 y 1, con 3 decimales.
     * @param parte la cantidad que se quiere medir
     * @param total la cantidad total
     * @return un valor pasado a long de tres decimales
     */
    public static double calcular(long parte, long total) {
        if (total == 0) {
            return 0.0;
        }
        return Math.round((double) parte / total * 1000.0) / 1000.0;
    }
}