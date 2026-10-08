package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

public record RegistroPrestamoSeed(
    String solicitante,
    String publicador,
    String libro,
    int diasDesdePrestamo,
    Integer diasDesdeDevolucion
) {}