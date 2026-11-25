package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PanelPrestamosService")
class PanelPrestamosServiceTest {

    @Mock RegistroPrestamoRepository repo;
    @InjectMocks PanelPrestamosService service;

    @Test 
    @DisplayName("Conteos y porcentaje: totales, activos, incompletos y demorados (incluye total 0)")
    void conteosYPorcentaje() {
        List<Estado> estadosActivos = List.of(
            Estado.aentregar, Estado.entregado, Estado.adevolver,
            Estado.demorado, Estado.enrevision, Estado.retirado
        );
        when(repo.count()).thenReturn(20L);
        when(repo.countByPublicacionEstadoPublicacionIn(estadosActivos)).thenReturn(8L);
        when(repo.countByFechaDevolucionIsNull()).thenReturn(10L);
        when(repo.countByPublicacionEstadoPublicacion(Estado.demorado)).thenReturn(3L);

        assertEquals(20L, service.obtenerPrestamosTotales());
        assertEquals(8L, service.obtenerPrestamosActivos());
        assertEquals(10L, service.obtenerPrestamosIncompletos());
        assertEquals(3L, service.obtenerPrestamosDemorados());
        assertEquals(0.4, service.obtenerPorcentajePrestamosActivos(), 0.001);

        // Total 0 préstamos -> evita división por cero
        when(repo.count()).thenReturn(0L);
        assertEquals(0.0, service.obtenerPorcentajePrestamosActivos());
    }

    @Test 
    @DisplayName("Obtiene la cantidad de publicaciones completas de los 12 meses")
    void porMes() {
        PublicacionSocialRepository.ConteoMes marzo = mock(PublicacionSocialRepository.ConteoMes.class);
        when(marzo.getMes()).thenReturn(3);
        when(marzo.getCantidad()).thenReturn(14L);

        when(repo.contarPrestamosCompletadosPorMes(anyInt())).thenReturn(List.of(marzo));

        Map<String, Long> porMes = service.obtenerCantidadPrestamosCompletadosPorMes();
        assertEquals(12, porMes.size());
        assertTrue(porMes.containsValue(14L));
    }

    @Test 
    @DisplayName("Mapea todas las categorías en orden, según cantidad de prestamos")
    void porCategoria() {
        Categoria[] categorias = Categoria.values();
        when(repo.contarPrestamosPorCategoria(any())).thenReturn(6L);

        Map<String, Long> res = service.obtenerCantidadPrestamosPorCategoria();
        assertEquals(categorias.length, res.size());
        assertEquals(6L, res.get(categorias[0].getNombre()));
    }
}
