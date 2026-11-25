package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - TablaEstadisticaRegistroPrestamoService")
class TablaEstadisticaRegistroPrestamoServiceTest {

    @Mock RegistroPrestamoRepository repo;
    @InjectMocks TablaEstadisticaRegistroPrestamoService service;

    @Test 
    @DisplayName("Obtener los prestamos activos: filtra por estados activos y ordena por fechaPrestamo e id DESC")
    void obtenerPrestamosActivos() {
        List<Estado> estadosEsperados = List.of(
            Estado.aentregar, Estado.entregado, Estado.adevolver,
            Estado.demorado, Estado.enrevision, Estado.retirado
        );
        Page<RegistroPrestamo> pagina = new PageImpl<>(List.of(new RegistroPrestamo()));
        when(repo.findAllByPublicacionEstadoPublicacionIn(eq(estadosEsperados), any())).thenReturn(pagina);

        Page<RegistroPrestamo> res = service.obtenerPrestamosActivos(0, 10);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findAllByPublicacionEstadoPublicacionIn(eq(estadosEsperados), captor.capture());

        Pageable enviado = captor.getValue();
        assertEquals(1, res.getTotalElements());
        assertEquals(0, enviado.getPageNumber());
        assertEquals(10, enviado.getPageSize());
        assertEquals(Sort.Direction.DESC, enviado.getSort().getOrderFor("fechaPrestamo").getDirection());
        assertEquals(Sort.Direction.DESC, enviado.getSort().getOrderFor("id").getDirection());
    }
}
