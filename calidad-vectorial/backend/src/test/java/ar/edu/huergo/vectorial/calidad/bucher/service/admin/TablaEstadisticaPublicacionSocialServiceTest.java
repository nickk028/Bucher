package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import java.util.List;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - TablaEstadisticaPublicacionSocialService")
class TablaEstadisticaPublicacionSocialServiceTest {

    @Mock PublicacionSocialRepository repo;
    @InjectMocks TablaEstadisticaPublicacionSocialService service;

    @Test 
    @DisplayName("Filtra solo eliminadas o devuelve todas según esta")
    void filtroEliminadas() {
        Page<PublicacionSocial> pagina = new PageImpl<>(List.of(new PublicacionSocial()));
        when(repo.findAllByRegistroEliminacionIsNotNull(any())).thenReturn(pagina);
        when(repo.findAll(any(Pageable.class))).thenReturn(pagina);

        assertEquals(1, service.obtenerPublicacionesSociales("RECIENTES", true, 0, 10).getTotalElements());
        assertEquals(1, service.obtenerPublicacionesSociales("RECIENTES", false, 0, 10).getTotalElements());

        verify(repo).findAllByRegistroEliminacionIsNotNull(any());
        verify(repo).findAll(any(Pageable.class));
    }

    @Test 
    @DisplayName("Aplica el orden correspondiente según orden (RECIENTES, DENUNCIAS, USUARIO)")
    void ordenamientos() {
        when(repo.findAll(any(Pageable.class))).thenReturn(Page.empty());
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        service.obtenerPublicacionesSociales("RECIENTES", false, 0, 10);
        service.obtenerPublicacionesSociales("DENUNCIAS", false, 0, 10);
        service.obtenerPublicacionesSociales("USUARIO", false, 0, 10);

        verify(repo, times(3)).findAll(captor.capture());
        List<Pageable> enviados = captor.getAllValues();

        assertNotNull(enviados.get(0).getSort().getOrderFor("fechaCreacion"));
        assertNotNull(enviados.get(1).getSort().getOrderFor("reportes"));
        assertNotNull(enviados.get(2).getSort().getOrderFor("usuario.nickname"));
    }
}
