package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import java.util.List;

public record PublicacionSeed(
    String usuario,
    String libro,
    String descripcion,
    int limiteDias,
    Estado estadoPublicacion,
    String detallesEstadoLibro,
    List<String> detallesAdicionales
) {}