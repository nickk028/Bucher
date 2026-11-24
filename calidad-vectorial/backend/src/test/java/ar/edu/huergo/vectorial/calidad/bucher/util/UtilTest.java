package ar.edu.huergo.vectorial.calidad.bucher.util;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ar.edu.huergo.vectorial.calidad.bucher.util.TiempoUtil;
import ar.edu.huergo.vectorial.calidad.bucher.util.PaginacionUtil;
import ar.edu.huergo.vectorial.calidad.bucher.util.PasswordValidator;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.OrdenPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.OrdenTablaUsuario;

class UtilTest {
    @Test @DisplayName("TiempoUtil: 'Ahora', minutos, horas y días")
    void tiempo() {
        assertEquals("Ahora", TiempoUtil.formatearTiempoTranscurrido(LocalDateTime.now()));
        assertEquals("5 Minutos", TiempoUtil.formatearTiempoTranscurrido(LocalDateTime.now().minusMinutes(5).minusSeconds(1)));
        assertEquals("1 Hora", TiempoUtil.formatearTiempoTranscurrido(LocalDateTime.now().minusHours(1).minusSeconds(1)));
        assertEquals("2 Días", TiempoUtil.formatearTiempoTranscurrido(LocalDateTime.now().minusDays(2).minusSeconds(1)));
    }

    @Test @DisplayName("PaginacionUtil limita tamaño a 50 y página a >= 0")
    void paginacion() {
        Pageable p = PaginacionUtil.crearPageable(-3, 9999, Sort.unsorted());
        assertEquals(0, p.getPageNumber());
        assertEquals(50, p.getPageSize());
        assertEquals(1, PaginacionUtil.crearPageable(0, 0, Sort.unsorted()).getPageSize());
    }

    @Test @DisplayName("PasswordValidator acepta fuerte y rechaza débil/null")
    void password() {
        assertTrue(PasswordValidator.isValid("LectorSuperSegura@123"));
        assertFalse(PasswordValidator.isValid("corta"));
        assertFalse(PasswordValidator.isValid("sinmayusculas@1234567"));
        assertFalse(PasswordValidator.isValid(null));
        assertThrows(IllegalArgumentException.class, () -> PasswordValidator.validate("x"));
    }

    @Test @DisplayName("Enums con valor inválido caen al valor por defecto")
    void enums() {
        assertEquals(Estado.indefinido, Estado.from("inexistente"));
        assertEquals(EstadoLectura.Indefinido, EstadoLectura.from(null));
        assertEquals(OrdenPublicacionSocial.RECIENTES, OrdenPublicacionSocial.from("xxx"));
        assertEquals(OrdenTablaUsuario.DENUNCIAS, OrdenTablaUsuario.from("denuncias"));
    }
}