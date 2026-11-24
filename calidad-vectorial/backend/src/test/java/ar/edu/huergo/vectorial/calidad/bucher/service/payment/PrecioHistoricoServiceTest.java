package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoLibro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoLibroRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PrecioHistoricoService")
public class PrecioHistoricoServiceTest {

    @Mock
    private PrecioHistoricoLibroRepository precioHistoricoLibroRepository;

    @InjectMocks
    private PrecioHistoricoLibroService precioHistoricoService;

    private PrecioHistoricoLibro precioViejo;
    private PrecioHistoricoLibro precioNuevo;
    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        Autor autor = new Autor(
            "Gabriel García Márquez",
            "https://es.wikipedia.org/wiki/Gabriel_García_Márquez"
        );
        Editorial editorial = new Editorial(
            "Editorial Sudamericana",
            "https://es.wikipedia.org/wiki/Editorial_Sudamericana"
        );

        libroEjemplo = new Libro();
        libroEjemplo.setId(1L);
        libroEjemplo.setTitulo("Cien Años de Soledad");
        libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo.setPaginas(417);
        libroEjemplo.setEdicion("Primera edición");
        libroEjemplo.setCalificacion(90);
        libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo.setAutor(autor);
        libroEjemplo.setEditorial(editorial);

        precioViejo = new PrecioHistoricoLibro();
        precioViejo.setLibro(libroEjemplo);
        precioViejo.setPrecio(new BigDecimal("50.00"));
        precioViejo.setFechaModificacion(LocalDate.of(2026,9,10));

        precioNuevo = new PrecioHistoricoLibro();
        precioNuevo.setLibro(libroEjemplo);
        precioNuevo.setPrecio(new BigDecimal("80.00"));
        precioNuevo.setFechaModificacion(LocalDate.of(2026,9,20));
    }

    @Test
    @DisplayName("Debería crear un precio histórico asociado al libro")
    void deberiaCrearPrecioHistorico() {
        // Given
        BigDecimal precio = new BigDecimal("70.00");
        when(
            precioHistoricoLibroRepository.save(any(PrecioHistoricoLibro.class))).thenAnswer((invocacion) -> invocacion.getArgument(0));

        // When
        PrecioHistoricoLibro resultado = precioHistoricoService.crearPrecioHistorico(libroEjemplo, precio);

        // Then
        assertNotNull(resultado);
        ArgumentCaptor<PrecioHistoricoLibro> captor = ArgumentCaptor.forClass(PrecioHistoricoLibro.class);
        verify(precioHistoricoLibroRepository, times(1)).save(captor.capture());
        assertSame(libroEjemplo, captor.getValue().getLibro());
        assertEquals(0, precio.compareTo(captor.getValue().getPrecio()));
    }

    @Test
    @DisplayName("Debería obtener el último precio histórico")
    void deberiaObtenerUltimoPrecio() {
        // Given
        when(
            precioHistoricoLibroRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(
                libroEjemplo
            )
        ).thenReturn(Optional.of(precioNuevo));

        // When
        PrecioHistoricoLibro resultado = precioHistoricoService.obtenerUltimoPrecio(libroEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(precioNuevo, resultado);
        verify(
            precioHistoricoLibroRepository,
            times(1)
        ).findFirstByLibroOrderByFechaModificacionDescIdDesc(libroEjemplo);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el libro no tiene precios históricos")
    void deberiaLanzarExcepcionSiNoHayPrecios() {
        // Given
        when(
            precioHistoricoLibroRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(
                libroEjemplo
            )
        ).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () ->
            precioHistoricoService.obtenerUltimoPrecio(libroEjemplo)
        );
    }

    @Test
    @DisplayName("Debería obtener el precio actual del libro")
    void deberiaObtenerPrecioActual() {
        // Given
        when(
            precioHistoricoLibroRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(
                libroEjemplo
            )
        ).thenReturn(Optional.of(precioNuevo));

        // When
        BigDecimal resultado = precioHistoricoService.obtenerPrecioActual(libroEjemplo);

        // Then
        assertNotNull(resultado);
        assertEquals(0, new BigDecimal("80.00").compareTo(resultado));
    }

    @Test
    @DisplayName("Debería devolver null como precio actual si no hay precios históricos")
    void deberiaDevolverNullSiNoHayPrecioActual() {
        // Given
        when(
            precioHistoricoLibroRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(
                libroEjemplo
            )
        ).thenReturn(Optional.empty());

        // When
        BigDecimal resultado = precioHistoricoService.obtenerPrecioActual(libroEjemplo);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Debería obtener el historial de precios del libro")
    void deberiaObtenerHistorialPrecios() {
        // Given
        when(
            precioHistoricoLibroRepository.findAllByLibroOrderByFechaModificacionDescIdDesc(
                libroEjemplo
            )
        ).thenReturn(List.of(precioNuevo, precioViejo));

        // When
        List<PrecioHistoricoLibro> resultado = precioHistoricoService.obtenerHistorialPrecios(
            libroEjemplo
        );

        // Then
        assertEquals(2, resultado.size());
        assertEquals(precioNuevo, resultado.get(0));
        assertEquals(precioViejo, resultado.get(1));
    }
}
