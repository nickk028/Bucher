package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.TipoVoto;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.VotoPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.VotoPublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - VotoPublicacionSocialService")
class VotoPublicacionSocialServiceTest {

    @Mock VotoPublicacionSocialRepository repo;
    @Mock PublicacionSocialRepository pubRepo;
    @Mock PublicacionSocialService pubService;
    @Mock UsuarioService usuarioService;
    @InjectMocks VotoPublicacionSocialService service;

    private Usuario usuario;
    private PublicacionSocial pub;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("lector@gmail.com");

        pub = new PublicacionSocial();
        pub.setId(10L);
    }

    @Test 
    @DisplayName("Votar: crea voto nuevo, suma contador y traduce concurrencia a IllegalArgumentException")
    void votarNuevo() {
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(pubService.obtenerPublicacionSocialPorId(10L)).thenReturn(pub);
        when(repo.findByUsuarioIdAndPublicacionSocialId(1L, 10L)).thenReturn(Optional.empty());

        service.votarPublicacion(10L, TipoVoto.POSITIVO, "lector@gmail.com");
        verify(repo).saveAndFlush(any(VotoPublicacionSocial.class));
        verify(pubRepo).sumarVotosPositivos(10L, 1);

        //En caso de concurrencia
        doThrow(DataIntegrityViolationException.class).when(repo).saveAndFlush(any());
        assertThrows(IllegalArgumentException.class,
            () -> service.votarPublicacion(10L, TipoVoto.NEGATIVO, "lector@gmail.com"));
    }

    @Test 
    @DisplayName("Cambiar voto existente: si ya votó distinto cambia tipo y ajusta ambos contadores; si es igual no hace nada")
    void cambiarOIgualVoto() {
        VotoPublicacionSocial existente = new VotoPublicacionSocial();
        existente.setTipo(TipoVoto.POSITIVO);

        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(pubService.obtenerPublicacionSocialPorId(10L)).thenReturn(pub);
        when(repo.findByUsuarioIdAndPublicacionSocialId(1L, 10L)).thenReturn(Optional.of(existente));

        // Mismo voto
        service.votarPublicacion(10L, TipoVoto.POSITIVO, "lector@gmail.com");
        verify(repo, never()).saveAndFlush(any());

        // Cambia el voto a uno negativo
        service.votarPublicacion(10L, TipoVoto.NEGATIVO, "lector@gmail.com");
        assertEquals(TipoVoto.NEGATIVO, existente.getTipo());
        verify(pubRepo).sumarVotosPositivos(10L, -1);
        verify(pubRepo).sumarVotosNegativos(10L, 1);
    }

    @Test 
    @DisplayName("Votar publicacion eliminada: rechaza si la publicación está eliminada")
    void votarEliminada() {
        pub.setRegistroEliminacion(new RegistroEliminacionPublicacionSocial());
        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(pubService.obtenerPublicacionSocialPorId(10L)).thenReturn(pub);

        assertThrows(IllegalArgumentException.class,
            () -> service.votarPublicacion(10L, TipoVoto.POSITIVO, "lector@gmail.com"));
        verify(repo, never()).saveAndFlush(any());
    }

    @Test 
    @DisplayName("Eliminar voto: saca voto y resta contador; lanza EntityNotFoundException si no existía")
    void quitarVoto() {
        VotoPublicacionSocial voto = new VotoPublicacionSocial();
        voto.setTipo(TipoVoto.NEGATIVO);

        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(repo.findByUsuarioIdAndPublicacionSocialId(1L, 10L))
            .thenReturn(Optional.of(voto))
            .thenReturn(Optional.empty());

        service.quitarVoto(10L, "lector@gmail.com");
        verify(repo).delete(voto);
        verify(pubRepo).sumarVotosNegativos(10L, -1);

        assertThrows(EntityNotFoundException.class,
            () -> service.quitarVoto(10L, "lector@gmail.com"));
    }

    @Test 
    @DisplayName("Obtener votos: devuelve un mapa por el ID de publicación o vacio si no hay IDs")
    void obtenerVotos() {
        VotoPublicacionSocial voto = new VotoPublicacionSocial();
        voto.setPublicacionSocial(pub);
        voto.setTipo(TipoVoto.POSITIVO);

        when(usuarioService.obtenerUsuarioPorNombre("lector@gmail.com")).thenReturn(usuario);
        when(repo.findAllByUsuarioIdAndPublicacionSocialIdIn(1L, List.of(10L))).thenReturn(List.of(voto));

        assertTrue(service.obtenerVotosUsuario("lector@gmail.com", List.of()).isEmpty());
        Map<Long, TipoVoto> mapa = service.obtenerVotosUsuario("lector@gmail.com", List.of(10L));
        assertEquals(TipoVoto.POSITIVO, mapa.get(10L));
    }
}

