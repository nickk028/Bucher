package ar.edu.huergo.vectorial.calidad.bucher.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Clase utilitaria para el manejo de tiempos.
 * Provee métodos para mostrar hace cuánto ocurrió una fecha.
 */
public class TiempoUtil {

    /**
     * Calcula hace cuánto ocurrió una fecha, expresado en la unidad mayor que corresponda.
     * @param fecha la fecha y hora a comparar contra el momento actual
     * @return un texto como "Ahora", "5 Minutos", "1 Día" o "2 Años"
     */
    public static String formatearTiempoTranscurrido(LocalDateTime fecha) {
        LocalDateTime ahora = LocalDateTime.now();

        long minutos = ChronoUnit.MINUTES.between(fecha, ahora);
        if (minutos < 1) {
            return "Ahora";
        }

        long horas = ChronoUnit.HOURS.between(fecha, ahora);
        if (horas < 1) {
            return formatearUnidad(minutos, "Minuto", "Minutos");
        }

        long dias = ChronoUnit.DAYS.between(fecha, ahora);
        if (dias < 1) {
            return formatearUnidad(horas, "Hora", "Horas");
        }

        long semanas = ChronoUnit.WEEKS.between(fecha, ahora);
        if (semanas < 1) {
            return formatearUnidad(dias, "Día", "Días");
        }

        long meses = ChronoUnit.MONTHS.between(fecha, ahora);
        if (meses < 1) {
            return formatearUnidad(semanas, "Semana", "Semanas");
        }

        long anios = ChronoUnit.YEARS.between(fecha, ahora);
        if (anios < 1) {
            return formatearUnidad(meses, "Mes", "Meses");
        }

        return formatearUnidad(anios, "Año", "Años");
    }

    private static String formatearUnidad(long cantidad, String singular, String plural) {
        return cantidad + " " + (cantidad == 1 ? singular : plural);
    }
}