package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PanelPosteosService")
class PanelPosteosServiceTest {

    @Mock PublicacionSocialRepository pubRepo;
    @Mock LibroRepository libroRepo;
    @InjectMocks PanelPosteosService service;

    @Test 
    @DisplayName("Conteos y porcentajes: denunciados, eliminados, referencias y menciones (incluye total 0)")
    void conteosYPorcentajes() {
        when(pubRepo.count()).thenReturn(20L);
        when(pubRepo.countByPublicacionReferenciadaIsNotNull()).thenReturn(6L);
        when(pubRepo.countByReportesGreaterThanAndRegistroEliminacionIsNull(0)).thenReturn(4L);
        when(pubRepo.countByRegistroEliminacionIsNotNull()).thenReturn(2L);
        when(pubRepo.countByLibroIsNotNull()).thenReturn(10L);

        assertEquals(20L, service.obtenerPosteosPublicados());
        assertEquals(6L, service.obtenerPosteosConReferencias());
        assertEquals(4L, service.obtenerPosteosDenunciados());
        assertEquals(2L, service.obtenerPosteosEliminados());

        assertEquals(0.2, service.obtenerPorcentajePosteosDenunciados(), 0.001);
        assertEquals(0.1, service.obtenerPorcentajePosteosEliminados(), 0.001);
        assertEquals(0.5, service.obtenerPorcentajePosteosConMenciones(), 0.001);

        // Evita la division por 0 - Error
        when(pubRepo.count()).thenReturn(0L);
        assertEquals(0.0, service.obtenerPorcentajePosteosDenunciados());
    }

    @Test 
    @DisplayName("Completa los 12 meses y agrupa la cantidad de posteos")
    void mesesYCategorias() {
        PublicacionSocialRepository.ConteoMes enero = mock(PublicacionSocialRepository.ConteoMes.class);
        when(enero.getMes()).thenReturn(1);
        when(enero.getCantidad()).thenReturn(12L);

        when(pubRepo.contarPosteosPorMes(anyInt())).thenReturn(List.of(enero));
        when(pubRepo.contarPosteosRaizPorCategoria(any())).thenReturn(5L);

        Map<String, Long> porMes = service.obtenerCantidadPosteosPorMes();
        assertEquals(12, porMes.size());
        assertTrue(porMes.containsValue(12L));

        Map<String, Double> cats = service.obtenerCategoriasMasMencionadas();
        assertFalse(cats.isEmpty());
    }

    @Test
    @DisplayName("Arma el ranking ordenado y omite IDs nulos de los libros más mencionados")
    void rankingLibros() {
        Libro l1 = new Libro(); l1.setId(1L); l1.setTitulo("Rayuela");
        Libro l2 = new Libro(); l2.setId(2L); l2.setTitulo("Ficciones");

        LibroUsuarioRepository.ConteoLibro c1 = mock(LibroUsuarioRepository.ConteoLibro.class);
        when(c1.getLibroId()).thenReturn(1L);
        when(c1.getCantidad()).thenReturn(18L);

        LibroUsuarioRepository.ConteoLibro c2 = mock(LibroUsuarioRepository.ConteoLibro.class);
        when(c2.getLibroId()).thenReturn(2L);
        when(c2.getCantidad()).thenReturn(9L);

        LibroUsuarioRepository.ConteoLibro cInexistente = mock(LibroUsuarioRepository.ConteoLibro.class);
        when(cInexistente.getLibroId()).thenReturn(99L);

        when(pubRepo.contarLibrosMasMencionados(any())).thenReturn(List.of(c1, c2, cInexistente));
        when(libroRepo.findAllById(List.of(1L, 2L, 99L))).thenReturn(List.of(l2, l1));

        Map<Libro, Long> ranking = service.obtenerLibrosMasMencionados();
        assertEquals(List.of(l1, l2), List.copyOf(ranking.keySet()));
        assertEquals(18L, ranking.get(l1));
        assertEquals(9L, ranking.get(l2));
    }
}
