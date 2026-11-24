package ar.edu.huergo.vectorial.calidad.bucher.service.suscription;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.RegistroSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.RegistroSuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.MedioPagoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import ar.edu.huergo.vectorial.calidad.bucher.service.subscription.RegistroSuscripcionService;
import ar.edu.huergo.vectorial.calidad.bucher.service.subscription.SuscripcionService;


@ExtendWith(MockitoExtension.class)
class RegistroSuscripcionServiceTest {
    @Mock RegistroSuscripcionRepository registroSuscripcionRepository;
    @Mock UsuarioRepository usuarioRepository;
    @Mock RegistroPrestamoRepository registroPrestamoRepository;
    @Mock SuscripcionService suscripcionService;
    @Mock MedioPagoService medioPagoService;
    @Mock PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;
    @InjectMocks RegistroSuscripcionService service;

    private Suscripcion plan; private MedioPago visa; private Usuario usuario;

    @BeforeEach void setUp() {
        ReflectionTestUtils.setField(service, "ivaPorcentaje", new BigDecimal("21"));
        usuario = new Usuario(); usuario.setUsername("u@test.com");
        plan = new Suscripcion(); plan.setId(2L);
        visa = new MedioPago(); visa.setId(2L); visa.setPorcentajeComision(3);
    }

    @Test @DisplayName("Calcula IVA y comisión correctamente")
    void calculaMontos() {
        when(usuarioRepository.findByUsername("u@test.com")).thenReturn(Optional.of(usuario));
        when(suscripcionService.obtenerSuscripcionPorId(2L)).thenReturn(plan);
        when(suscripcionService.esSuscripcionGratuita(plan)).thenReturn(false);
        when(medioPagoService.obtenerMedioPagoPorId(2L)).thenReturn(visa);
        when(medioPagoService.esMedioPagoVisa(visa)).thenReturn(true);
        when(precioHistoricoSuscripcionService.obtenerPrecioActual(plan)).thenReturn(new BigDecimal("4999.00"));
        when(registroSuscripcionRepository.findFirstByUsuarioAndFechaFinAfterOrderByFechaFinDesc(any(), any()))
            .thenReturn(Optional.empty());
        when(registroSuscripcionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RegistroSuscripcion r = service.contratarSuscripcion(
            "u@test.com", 2L, 2L, "4111111111111111", "12/30", "123", "TITULAR DE PRUEBA");

        assertEquals(0, new BigDecimal("6048.79").compareTo(r.getMontoPagado()));
        assertEquals(0, new BigDecimal("5867.33").compareTo(r.getMontoAcreditado()));
    }

    @Test @DisplayName("Tarjeta inválida lanza IllegalArgumentException")
    void tarjetaInvalida() {
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.of(usuario));
        when(suscripcionService.obtenerSuscripcionPorId(2L)).thenReturn(plan);
        when(suscripcionService.esSuscripcionGratuita(plan)).thenReturn(false);
        when(medioPagoService.obtenerMedioPagoPorId(2L)).thenReturn(visa);
        when(medioPagoService.esMedioPagoVisa(visa)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            service.contratarSuscripcion("u@test.com", 2L, 2L, "1111", "12/30", "123", "X"));
    }
}