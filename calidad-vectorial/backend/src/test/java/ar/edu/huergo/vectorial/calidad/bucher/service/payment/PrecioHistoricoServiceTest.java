package ar.edu.huergo.vectorial.calidad.bucher.service.payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistorico;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.PrecioHistoricoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PrecioHistoricoService")
public class PrecioHistoricoServiceTest {

    @Mock
    private PrecioHistoricoRepository precioHistoricoRepository;

    @InjectMocks
    private PrecioHistoricoService precioHistoricoService;

    private Autor autorEjemplo;
    private Editorial editorialEjemplo;
    private Libro libroEjemplo;

    private PrecioHistorico ejemploPrecioHistorico;
    private PrecioHistorico ejemplo2PrecioHistorico;

    @BeforeEach
    void setUp() {
        autorEjemplo = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
        editorialEjemplo = new Editorial("Editorial Sudamericana",
                "https://es.wikipedia.org/wiki/Editorial_Sudamericana");
        
        ejemploPrecioHistorico = new PrecioHistorico();
        ejemploPrecioHistorico.setPrecio(1.00);
        ejemploPrecioHistorico.setFechaModificacion(LocalDate.of(2026, 9, 9));

        ejemplo2PrecioHistorico = new PrecioHistorico();
        ejemplo2PrecioHistorico.setPrecio(2.00);
        ejemplo2PrecioHistorico.setFechaModificacion(LocalDate.of(2026, 9, 10));

        
        libroEjemplo = new Libro();
        libroEjemplo.setTitulo("Cien Años de Soledad");
        libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
        libroEjemplo.setPaginas(417);
        libroEjemplo.setEdicion("Primera edición");
        libroEjemplo.setCalificacion(90);
        libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
        libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
        libroEjemplo.setPrecio(1500.00);
        libroEjemplo.setCategoria(Set.of(Categoria.realismomagico));
        libroEjemplo.setAutor(autorEjemplo);
        libroEjemplo.setEditorial(editorialEjemplo);
        libroEjemplo.setPreciosHistoricos(List.of(ejemploPrecioHistorico, ejemplo2PrecioHistorico));

        ejemploPrecioHistorico.setLibro(libroEjemplo);
        ejemplo2PrecioHistorico.setLibro(libroEjemplo);
    }

    @Test
    @DisplayName("Debería obtener obtener el último precio histórico")
    void deberiaObtenerUltimoPrecio() {

        // Given
        Libro libroBuscado = libroEjemplo;
        Optional<PrecioHistorico> precioHistoricoEncontrado = Optional.of(ejemplo2PrecioHistorico);
        when(precioHistoricoRepository.findFirstByLibroOrderByFechaModificacionDescIdDesc(libroBuscado))
                .thenReturn(precioHistoricoEncontrado);

        // When
        PrecioHistorico resultado = precioHistoricoService.obtenerUltimoPrecio(libroBuscado);

        // Then
        assertNotNull(resultado);
        assertEquals(ejemplo2PrecioHistorico, resultado);
        verify(precioHistoricoRepository, times(1)).findFirstByLibroOrderByFechaModificacionDescIdDesc(libroBuscado);
    }
}