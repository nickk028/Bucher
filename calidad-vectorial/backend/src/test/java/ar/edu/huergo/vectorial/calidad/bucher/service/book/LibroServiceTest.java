package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - LibroService")
public class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    private Autor autorEjemplo;
    private Editorial editorialEjemplo;
    private Libro libroEjemplo;

    @BeforeEach
        void setUp() {
            autorEjemplo = new Autor("Gabriel García Márquez", "https://es.wikipedia.org/wiki/Gabriel_García_Márquez");
            editorialEjemplo = new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana");

            libroEjemplo = new Libro();
            libroEjemplo.setTitulo("Cien Años de Soledad");
            libroEjemplo.setDescripcion("Una novela emblemática del realismo mágico.");
            libroEjemplo.setPaginas(417);
            libroEjemplo.setEdicion("Primera edición");
            libroEjemplo.setCalificacion(90);
            libroEjemplo.setFechaPublicacion(LocalDate.of(1967, 5, 30));
            libroEjemplo.setUrlFoto("http://imagen.com/portada.jpg");
            libroEjemplo.setPrecio(1500.00);
            libroEjemplo.setCategoria(Set.of(Categoria.Realismo_Mágico));
            libroEjemplo.setAutor(autorEjemplo);
            libroEjemplo.setEditorial(editorialEjemplo);
        }

    @Test
    @DisplayName("Debería buscar libro por título (case insensitive)")
    void deberiaBuscarLibroPorTitulo() {
        // Given
        String tituloBusqueda = "Cien Años de Soledad";
        Optional<Libro> libroEncontrado = Optional.of(libroEjemplo);
        when(libroRepository.findByTituloIgnoringCase(tituloBusqueda))
                .thenReturn(libroEncontrado);

        // When
        Libro resultado = libroService.obtenerLibroPorTitulo(tituloBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(libroEjemplo.getTitulo(), resultado.getTitulo());
        verify(libroRepository, times(1)).findByTituloIgnoringCase(tituloBusqueda);
    }
}