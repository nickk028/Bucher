package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import java.math.BigDecimal;

public record SuscripcionSeed(
    String nombre,
    int porcentajeCobertura,
    int limiteLibros,
    BigDecimal precio
) {}