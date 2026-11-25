package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - AutorService")
class AutorServiceTest {

    @Mock AutorRepository repo;
    @InjectMocks AutorService service;

    @Test
    @DisplayName("Obtener todos, por ID y crear: devuelve entidad o lanza EntityNotFoundException")
    void crudBasico() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Borges");

        when(repo.findAll()).thenReturn(List.of(autor));
        when(repo.findById(1L)).thenReturn(Optional.of(autor));
        when(repo.findById(99L)).thenReturn(Optional.empty());
        when(repo.save(autor)).thenReturn(autor);

        assertEquals(1, service.obtenerTodosLosAutores().size());
        assertEquals("Borges", service.obtenerAutorPorId(1L).getNombre());
        assertThrows(EntityNotFoundException.class, () -> service.obtenerAutorPorId(99L));
        assertEquals(autor, service.crearAutor(autor));
    }

    @Test
    @DisplayName("Devuelve existente si ya tiene autor")
    void obtenerAutorExistente() {
        Usuario u = new Usuario();
        Autor existente = new Autor();
        existente.setNombre("Cortázar");

        when(repo.findByUsuario(u)).thenReturn(Optional.of(existente));

        assertSame(existente, service.obtenerOCrearAutorDeUsuario(u));
        verify(repo, never()).save(any());
    }

    @Test
    @DisplayName("Crea autor nuevo y agrega sufijo si el nickname ya existe")
    void crearAutorConNombreUnico() {
        Usuario u = new Usuario();
        u.setNickname("Gabo");
        u.setDescripcion("Escritor");
        u.setAvatar(Avatar.ALICIA);

        when(repo.findByUsuario(u)).thenReturn(Optional.empty());
        when(repo.existsByNombreIgnoringCase("Gabo")).thenReturn(true);
        when(repo.existsByNombreIgnoringCase("Gabo01")).thenReturn(false);
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        Autor creado = service.obtenerOCrearAutorDeUsuario(u);

        assertEquals("Gabo01", creado.getNombre());
        assertEquals("Escritor", creado.getDescripcion());
        assertSame(u, creado.getUsuario());
    }
}
