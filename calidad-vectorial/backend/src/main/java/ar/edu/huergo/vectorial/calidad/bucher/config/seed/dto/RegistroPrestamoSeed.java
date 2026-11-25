package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import java.util.List;

public record RegistroPrestamoSeed(
    String solicitante,
    String publicador,
    String libro,
    int diasDesdePrestamo,
    Integer diasDesdeDevolucion,
    String confirmacionTitulo,
    String confirmacionAutor,
    String confirmacionDetallesEstado,
    List<String> confirmacionDetallesEstadoLibro,
    String comentariosAdicionales,
    String estaEnCondiciones,
    String motivoNoEnCondiciones
) {}