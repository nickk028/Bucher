package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.ReportePublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.ReportePublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - ReportePublicacionSocialService")
class ReportePublicacionSocialServiceTest {

    @Mock ReportePublicacionSocialRepository repo;
    @Mock PublicacionSocialRepository pubRepo;
    @Mock PublicacionSocialService pubService;
    @Mock UsuarioService usuarioService;
    @InjectMocks ReportePublicacionSocialService service;

    private Usuario autor;
    private Usuario reportador;
    private PublicacionSocial pub;

    @BeforeEach
    void setUp() {
        autor = new Usuario();
        autor.setId(1L);

        reportador = new Usuario();
        reportador.setId(2L);
        reportador.setUsername("lector2@gmail.com");

        pub = new PublicacionSocial();
        pub.setId(10L);
        pub.setUsuario(autor);
    }

    @Test 
    @DisplayName("Reportar: guarda reporte, suma contador y traduce concurrencia a IllegalArgumentException")
    void reportarOkYConcurrencia() {
        when(usuarioService.obtenerUsuarioPorNombre("lector2@gmail.com")).thenReturn(reportador);
        when(pubService.obtenerPublicacionSocialPorId(10L)).thenReturn(pub);
        when(repo.existsByUsuarioIdAndPublicacionSocialId(2L, 10L)).thenReturn(false);

        service.reportarPublicacion(10L, "lector2@gmail.com");
        verify(repo).saveAndFlush(any(ReportePublicacionSocial.class));
        verify(pubRepo).sumarReportes(10L, 1);

        // Colisión concurrente (DataIntegrityViolationException)
        doThrow(DataIntegrityViolationException.class).when(repo).saveAndFlush(any());
        assertThrows(IllegalArgumentException.class,
            () -> service.reportarPublicacion(10L, "lector2@gmail.com"));
    }

    @Test 
    @DisplayName("Reportar: rechaza si está eliminada, es propia o ya fue reportada")
    void reportarRechazos() {
        when(usuarioService.obtenerUsuarioPorNombre("autor@gmail.com")).thenReturn(autor);
        when(usuarioService.obtenerUsuarioPorNombre("lector2@gmail.com")).thenReturn(reportador);
        when(pubService.obtenerPublicacionSocialPorId(10L)).thenReturn(pub);

        // Publicación propia -> error
        assertThrows(IllegalArgumentException.class,
            () -> service.reportarPublicacion(10L, "autor@gmail.com"));

        // Ya reportada -> error
        when(repo.existsByUsuarioIdAndPublicacionSocialId(2L, 10L)).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
            () -> service.reportarPublicacion(10L, "lector2@gmail.com"));

        // Eliminada -> error
        pub.setRegistroEliminacion(new RegistroEliminacionPublicacionSocial());
        assertThrows(IllegalArgumentException.class,
            () -> service.reportarPublicacion(10L, "lector2@gmail.com"));

        verify(repo, never()).saveAndFlush(any());
        verify(pubRepo, never()).sumarReportes(any(), anyInt());
    }
}