package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;

public record LibroUsuarioSeed(
    String usuario,
    String libro,
    int paginaActual,
    EstadoLectura estadoLectura,
    int puntuacion
) {}