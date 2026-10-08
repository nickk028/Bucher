package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;

public record PublicacionSeed(
    String usuario,
    String libro,
    String descripcion,
    int limiteDias,
    Estado estadoPublicacion
) {}