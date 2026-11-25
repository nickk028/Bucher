package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoSuscripcionRepository;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PrecioHistoricoSuscripcionService")
class PrecioHistoricoSuscripcionServiceTest {

    @Mock PrecioHistoricoSuscripcionRepository repo;
    @InjectMocks PrecioHistoricoSuscripcionService service;

    @Test
        @DisplayName("Crea el precio historico; guarda y devuelve registro con suscripción y precio")
    void crear() {
        Suscripcion sub = new Suscripcion();
        BigDecimal precio = new BigDecimal("49.99");
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        PrecioHistoricoSuscripcion res = service.crearPrecioHistorico(sub, precio);

        assertEquals(sub, res.getSuscripcion());
        assertEquals(precio, res.getPrecio());
        verify(repo).save(any(PrecioHistoricoSuscripcion.class));
    }

    @Test
        @DisplayName("Obtiene el precio actual; da el ultimo precio o null si no tiene historial")
    void obtenerActual() {
        Suscripcion sub = new Suscripcion();
        PrecioHistoricoSuscripcion hist = new PrecioHistoricoSuscripcion();
        hist.setPrecio(new BigDecimal("60.79"));

        when(repo.findFirstBySuscripcionOrderByFechaModificacionDescIdDesc(sub))
            .thenReturn(Optional.of(hist))
            .thenReturn(Optional.empty());

        assertEquals(new BigDecimal("60.79"), service.obtenerPrecioActual(sub));
        assertNull(service.obtenerPrecioActual(sub));
    }
}

