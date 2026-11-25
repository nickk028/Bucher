package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import java.util.List;
import java.util.Map;
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
@DisplayName("Tests Unitarios - TablaEstadisticaUsuarioService")
class TablaEstadisticaUsuarioServiceTest {

    @Mock UsuarioRepository usuarioRepo;
    @Mock PublicacionRepository pubRepo;
    @Mock PublicacionSocialRepository pubSocialRepo;
    @InjectMocks TablaEstadisticaUsuarioService service;

    @Test 
    @DisplayName("Obtener Usuarios: ordena por ID DESC o llama a consulta por denuncias según orden")
    void obtenerUsuarios() {
        Page<Usuario> pagina = new PageImpl<>(List.of(new Usuario()));
        when(usuarioRepo.findAll(any(Pageable.class))).thenReturn(pagina);
        when(usuarioRepo.findAllOrdenadosPorDenuncias(any(Pageable.class))).thenReturn(pagina);

        ArgumentCaptor<Pageable> captorId = ArgumentCaptor.forClass(Pageable.class);
        assertEquals(1, service.obtenerUsuarios("ID", 0, 10).getTotalElements());
        verify(usuarioRepo).findAll(captorId.capture());
        assertEquals(Sort.Direction.DESC, captorId.getValue().getSort().getOrderFor("id").getDirection());

        assertEquals(1, service.obtenerUsuarios("DENUNCIAS", 0, 10).getTotalElements());
        verify(usuarioRepo).findAllOrdenadosPorDenuncias(any(Pageable.class));
    }

    @Test 
    @DisplayName("Conteos por usuario: publicaciones, posteos y denuncias (incluye lista vacía)")
    void conteosPorUsuario() {
        List<Long> ids = List.of(1L, 2L);

        UsuarioRepository.ConteoUsuario c1 = mock(UsuarioRepository.ConteoUsuario.class);
        when(c1.getUsuarioId()).thenReturn(1L);
        when(c1.getCantidad()).thenReturn(4L);

        when(pubRepo.contarPublicacionesPorUsuario(ids)).thenReturn(List.of(c1));
        when(pubSocialRepo.contarPosteosPorUsuario(ids)).thenReturn(List.of(c1));
        when(pubSocialRepo.contarDenunciasPorUsuario(ids)).thenReturn(List.of(c1));

        assertTrue(service.obtenerCantidadPublicaciones(List.of()).isEmpty());
        assertTrue(service.obtenerCantidadPosteos(List.of()).isEmpty());
        assertTrue(service.obtenerCantidadDenuncias(List.of()).isEmpty());

        assertEquals(Map.of(1L, 4L), service.obtenerCantidadPublicaciones(ids));
        assertEquals(Map.of(1L, 4L), service.obtenerCantidadPosteos(ids));
        assertEquals(Map.of(1L, 4L), service.obtenerCantidadDenuncias(ids));
    }
}