package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.EditorialService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PanelLibrosService")
class PanelLibrosServiceTest {

    @Mock LibroRepository libroRepo;
    @Mock LibroUsuarioRepository luRepo;
    @Mock PublicacionSocialRepository pubSocialRepo;
    @Mock EditorialService editorialService;
    @InjectMocks PanelLibrosService service;

    @Test 
    @DisplayName("Devuelve conteos de los libros de editoriales y propios")
    void conteosLibros() {
        Editorial propia = new Editorial();
        when(libroRepo.count()).thenReturn(20L);
        when(editorialService.obtenerEditorialPropia()).thenReturn(propia);
        when(libroRepo.countByEditorial(propia)).thenReturn(7L);

        assertEquals(20L, service.obtenerLibrosRegistrados());
        assertEquals(7L, service.obtenerLibrosIndependientes());
    }

    @Test 
    @DisplayName("Mapean todas las categorías")
    void categorias() {
        Categoria[] cats = Categoria.values();
        when(libroRepo.countByCategoriaContaining(any())).thenReturn(3L);
        when(luRepo.contarPorCategoria(any())).thenReturn(10L);

        Map<String, Long> porCat = service.obtenerCantidadLibrosPorCategoria();
        assertEquals(cats.length, porCat.size());
        assertEquals(3L, porCat.get(cats[0].getNombre()));

        Map<String, Double> masGuardadas = service.obtenerCategoriasMasGuardadas();
        assertFalse(masGuardadas.isEmpty());
    }

    @Test 
    @DisplayName("Arma ranking ordenado con entidad Libro y cantidad, según cuanto los guardaron")
    void rankingLibros() {
        Libro l1 = new Libro(); l1.setId(1L); l1.setTitulo("Dune");
        Libro l2 = new Libro(); l2.setId(2L); l2.setTitulo("1984");

        LibroUsuarioRepository.ConteoLibro c1 = mock(LibroUsuarioRepository.ConteoLibro.class);
        when(c1.getLibroId()).thenReturn(1L);
        when(c1.getCantidad()).thenReturn(15L);

        LibroUsuarioRepository.ConteoLibro c2 = mock(LibroUsuarioRepository.ConteoLibro.class);
        when(c2.getLibroId()).thenReturn(2L);
        when(c2.getCantidad()).thenReturn(9L);

        when(luRepo.contarLibrosMasGuardados(any())).thenReturn(List.of(c1, c2));
        when(libroRepo.findAllById(List.of(1L, 2L))).thenReturn(List.of(l2, l1));

        Map<Libro, Long> ranking = service.obtenerLibrosMasGuardados();
        assertEquals(List.of(l1, l2), List.copyOf(ranking.keySet()));
        assertEquals(15L, ranking.get(l1));
        assertEquals(9L, ranking.get(l2));
    }

    @Test @DisplayName("Porcentajes: guardados, nombrados en posteos y con publicaciones (incluye total 0)")
    void porcentajes() {
        when(libroRepo.count()).thenReturn(10L);
        when(luRepo.contarLibrosGuardados()).thenReturn(5L);
        when(pubSocialRepo.contarLibrosNombrados()).thenReturn(2L);
        when(libroRepo.countByPublicacionesIsNotEmpty()).thenReturn(8L);

        assertEquals(0.5, service.obtenerPorcentajeLibrosGuardados(), 0.001);
        assertEquals(0.2, service.obtenerPorcentajeLibrosNombradosEnPosteos(), 0.001);
        assertEquals(0.8, service.obtenerPorcentajeLibrosConPublicaciones(), 0.001);

        // Evita la division por 0 - Error
        when(libroRepo.count()).thenReturn(0L);
        assertEquals(0.0, service.obtenerPorcentajeLibrosGuardados());
    }
}
