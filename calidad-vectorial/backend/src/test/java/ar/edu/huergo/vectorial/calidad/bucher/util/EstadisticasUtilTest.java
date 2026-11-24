package ar.edu.huergo.vectorial.calidad.bucher.util;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.util.EstadisticasUtil;


class EstadisticasUtilTest {
    @Test @DisplayName("Porcentaje con total 0 devuelve 0")
    void calcularConTotalCero() {
        assertEquals(0.0, EstadisticasUtil.calcular(5, 0));
    }

    @Test @DisplayName("Completa los 12 meses con 0 donde no hay datos")
    void completarMeses() {
        Map<String, Long> r = EstadisticasUtil.completarCantidadesPorMes(Map.of(1, 5L, 12, 2L));
        assertEquals(12, r.size());
        assertEquals(5L, r.get("Enero"));
        assertEquals(0L, r.get("Febrero"));
    }

    @Test @DisplayName("Top 5 categorías + 'Otras'")
    void categoriasDestacadas() {
        Map<Categoria, Long> c = new EnumMap<>(Categoria.class);
        c.put(Categoria.aventura, 40L); c.put(Categoria.terror, 30L);
        c.put(Categoria.romance, 10L);  c.put(Categoria.drama, 8L);
        c.put(Categoria.humor, 6L);     c.put(Categoria.infantil, 4L);
        c.put(Categoria.fantastico, 2L);

        Map<String, Double> r = EstadisticasUtil.calcularCategoriasDestacadas(c);
        assertEquals(6, r.size());
        assertEquals(0.4, r.get("Aventura"), 0.001);
        assertEquals(0.06, r.get("Otras"), 0.001);
    }
}