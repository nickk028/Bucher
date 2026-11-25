package ar.edu.huergo.vectorial.calidad.bucher.service.subscription;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.SuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - SuscripcionService")
class SuscripcionServiceTest {

    @Mock
    private SuscripcionRepository suscripcionRepository;

    @Mock
    private PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    @InjectMocks
    private SuscripcionService suscripcionService;

    private Suscripcion gratis;
    private Suscripcion premium;

    @BeforeEach
    void setUp() {
        gratis = new Suscripcion();
        gratis.setId(SuscripcionService.ID_SUSCRIPCION_GRATUITA);
        gratis.setNombre("Plan Gratis");
        gratis.setPorcentajeCobertura(0);
        gratis.setLimiteLibros(0);

        premium = new Suscripcion();
        premium.setId(2L);
        premium.setNombre("Plan Premium");
        premium.setPorcentajeCobertura(80);
        premium.setLimiteLibros(5);
    }

    @Test
    @DisplayName("Obtiene todas las suscripciones y las devuelve en una lista")
    void obtenerTodasLasSuscripciones() {
        when(suscripcionRepository.findAll()).thenReturn(List.of(gratis, premium));

        List<Suscripcion> resultado = suscripcionService.obtenerTodasLasSuscripciones();

        assertEquals(2, resultado.size());
        verify(suscripcionRepository).findAll();
    }

    @Test
    @DisplayName("Obtener suscripcion mediante ID, la devuelve cuando existe")
    void obtenerSuscripcionPorId() {
        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));

        Suscripcion resultado = suscripcionService.obtenerSuscripcionPorId(2L);

        assertEquals("Plan Premium", resultado.getNombre());
    }

    @Test
    @DisplayName("Obtener suscripcion mediante ID, que da EntityNotFoundException cuando no existe")
    void obtenerSuscripcionPorIdNoExiste() {
        when(suscripcionRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
            EntityNotFoundException.class,
            () -> suscripcionService.obtenerSuscripcionPorId(99L)
        );
        assertEquals("Suscripción no encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("Suscripcion gratuita, busca por ID_SUSCRIPCION_GRATUITA")
    void obtenerSuscripcionGratuita() {
        when(suscripcionRepository.findById(1L)).thenReturn(Optional.of(gratis));

        Suscripcion resultado = suscripcionService.obtenerSuscripcionGratuita();

        assertEquals(1L, resultado.getId());
        assertEquals("Plan Gratis", resultado.getNombre());
    }

    @Test
    @DisplayName("Revisa si la suscripcion es gratuita, devuelve true solo si ID es 1L")
    void esSuscripcionGratuita() {
        assertTrue(suscripcionService.esSuscripcionGratuita(gratis));
        assertFalse(suscripcionService.esSuscripcionGratuita(premium));
        assertFalse(suscripcionService.esSuscripcionGratuita(null));
    }

    @Test
    @DisplayName("Crea la suscripcion y crea su precio histórico inicial")
    void crearSuscripcionCorrectamente() {
        BigDecimal precioInicial = new BigDecimal("50.00");
        when(suscripcionRepository.existsByNombreIgnoreCase("Plan Premium")).thenReturn(false);
        when(suscripcionRepository.save(premium)).thenReturn(premium);

        Suscripcion creada = suscripcionService.crearSuscripcion(premium, precioInicial);

        assertNotNull(creada);
        assertEquals("Plan Premium", creada.getNombre());
        verify(suscripcionRepository).save(premium);
        verify(precioHistoricoSuscripcionService).crearPrecioHistorico(premium, precioInicial);
    }

    @Test
    @DisplayName("Crea la suscripcion, dando IllegalArgumentException si el nombre ya existe")
    void crearSuscripcionNombreExistente() {
        when(suscripcionRepository.existsByNombreIgnoreCase("Plan Premium")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> suscripcionService.crearSuscripcion(premium, new BigDecimal("5000.00"))
        );
        assertEquals("Ya existe una suscripción con ese nombre", ex.getMessage());
        verify(suscripcionRepository, never()).save(any());
        verify(precioHistoricoSuscripcionService, never()).crearPrecioHistorico(any(), any());
    }

    @Test
    @DisplayName("Actualiza nombre, cobertura y límite cuando es válido")
    void modificarSuscripcionCorrectamente() {
        Suscripcion datosNuevos = new Suscripcion();
        datosNuevos.setNombre("Plan Gold");
        datosNuevos.setPorcentajeCobertura(90);
        datosNuevos.setLimiteLibros(10);

        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));
        when(suscripcionRepository.existsByNombreIgnoreCase("Plan Gold")).thenReturn(false);
        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Suscripcion modificada = suscripcionService.modificarSuscripcion(2L, datosNuevos);

        assertEquals("Plan Gold", modificada.getNombre());
        assertEquals(90, modificada.getPorcentajeCobertura());
        assertEquals(10, modificada.getLimiteLibros());
        verify(suscripcionRepository).save(premium);
    }

    @Test
    @DisplayName("Se permite mantener el mismo nombre sin lanzar error por duplicado")
    void modificarSuscripcionMismoNombre() {
        Suscripcion datosNuevos = new Suscripcion();
        datosNuevos.setNombre("plan premium"); // mismo nombre distinto case
        datosNuevos.setPorcentajeCobertura(85);
        datosNuevos.setLimiteLibros(6);

        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));
        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Suscripcion modificada = suscripcionService.modificarSuscripcion(2L, datosNuevos);

        assertEquals(85, modificada.getPorcentajeCobertura());
        verify(suscripcionRepository, never()).existsByNombreIgnoreCase(any());
    }

    @Test
    @DisplayName("Da IllegalArgumentException si el nuevo nombre pertenece a otra")
    void modificarSuscripcionNombreDuplicado() {
        Suscripcion datosNuevos = new Suscripcion();
        datosNuevos.setNombre("Plan Gratis");

        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));
        when(suscripcionRepository.existsByNombreIgnoreCase("Plan Gratis")).thenReturn(true);

        assertThrows(
            IllegalArgumentException.class,
            () -> suscripcionService.modificarSuscripcion(2L, datosNuevos)
        );
        verify(suscripcionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Crea nuevo precio historico si el precio es distinto")
    void modificarPrecioSuscripcion() {
        BigDecimal precioActual = new BigDecimal("50.00");
        BigDecimal precioNuevo = new BigDecimal("65.00");
        PrecioHistoricoSuscripcion nuevoRegistro = new PrecioHistoricoSuscripcion();

        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));
        when(precioHistoricoSuscripcionService.obtenerPrecioActual(premium)).thenReturn(precioActual);
        when(precioHistoricoSuscripcionService.crearPrecioHistorico(premium, precioNuevo))
            .thenReturn(nuevoRegistro);

        PrecioHistoricoSuscripcion resultado = suscripcionService.modificarPrecioSuscripcion(2L, precioNuevo);

        assertSame(nuevoRegistro, resultado);
        verify(precioHistoricoSuscripcionService).crearPrecioHistorico(premium, precioNuevo);
    }

    @Test
    @DisplayName("Declina modificar el precio del plan gratuito")
    void modificarPrecioSuscripcionGratuita() {
        when(suscripcionRepository.findById(1L)).thenReturn(Optional.of(gratis));

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> suscripcionService.modificarPrecioSuscripcion(1L, new BigDecimal("20.00"))
        );
        assertEquals("El precio de la suscripción gratuita no se puede modificar", ex.getMessage());
        verify(precioHistoricoSuscripcionService, never()).crearPrecioHistorico(any(), any());
    }

    @Test
    @DisplayName("Declina modificar el precio si el nuevo es igual al actual")
    void modificarPrecioSuscripcionMismoPrecio() {
        BigDecimal precioActual = new BigDecimal("50.00");
        when(suscripcionRepository.findById(2L)).thenReturn(Optional.of(premium));
        when(precioHistoricoSuscripcionService.obtenerPrecioActual(premium)).thenReturn(precioActual);

        assertThrows(
            IllegalArgumentException.class,
            () -> suscripcionService.modificarPrecioSuscripcion(2L, new BigDecimal("50.00"))
        );
        verify(precioHistoricoSuscripcionService, never()).crearPrecioHistorico(any(), any());
    }

}
