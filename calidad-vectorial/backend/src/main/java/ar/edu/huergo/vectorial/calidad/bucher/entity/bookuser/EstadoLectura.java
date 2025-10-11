package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;
// Enum para los estados de lectura de los bookusers

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EstadoLectura {
    leyendo,
    abandonado,
    pendiente,
    leido,
    indefinido;

    @JsonCreator
    public static EstadoLectura from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return indefinido;
        }

        try {
            return EstadoLectura.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return indefinido;
        }
    }
}