package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.MedioPagoRepository;
import jakarta.persistence.EntityNotFoundException;
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
@DisplayName("Tests Unitarios - MedioPagoService")
class MedioPagoServiceTest {

    @Mock MedioPagoRepository repo;
    @InjectMocks MedioPagoService service;

    private MedioPago sinPago;
    private MedioPago visa;

    @BeforeEach
    void setUp() {
        sinPago = new MedioPago();
        sinPago.setId(MedioPagoService.ID_MEDIO_PAGO_SIN_PAGO);
        sinPago.setNombre("Sin Pago");

        visa = new MedioPago();
        visa.setId(MedioPagoService.ID_MEDIO_PAGO_VISA);
        visa.setNombre("Visa");
    }

    @Test @DisplayName("Obtener todos, por ID y Sin Pago: devuelve entidad o lanza EntityNotFoundException")
    void obtenerPorIdYSinPago() {
        when(repo.findAll()).thenReturn(List.of(sinPago, visa));
        when(repo.findById(1L)).thenReturn(Optional.of(sinPago));
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertEquals(2, service.obtenerTodosLosMediosPago().size());
        assertEquals("Sin Pago", service.obtenerMedioPagoSinPago().getNombre());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerMedioPagoPorId(99L));
    }

    @Test @DisplayName("Obtener por nombre: ignora mayúsculas o lanza EntityNotFoundException")
    void obtenerPorNombre() {
        when(repo.findByNombreIgnoreCase("visa")).thenReturn(Optional.of(visa));
        when(repo.findByNombreIgnoreCase("inexistente")).thenReturn(Optional.empty());

        assertEquals(2L, service.obtenerMedioPagoPorNombre("visa").getId());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerMedioPagoPorNombre("inexistente"));
    }

    @Test @DisplayName("Medio de pago visa: identifica ID 2L y crearMedioPago persiste entidad")
    void visaYCrear() {
        when(repo.save(visa)).thenReturn(visa);

        assertTrue(service.esMedioPagoVisa(visa));
        assertFalse(service.esMedioPagoVisa(sinPago));
        assertFalse(service.esMedioPagoVisa(null));
        assertEquals(visa, service.crearMedioPago(visa));
        verify(repo).save(visa);
    }
}

