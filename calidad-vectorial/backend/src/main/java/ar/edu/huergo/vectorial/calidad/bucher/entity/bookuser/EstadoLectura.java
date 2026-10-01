package ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser;

// Enum para los estados de lectura de los bookusers

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EstadoLectura {
    Leyendo,
    Abandonado,
    Pendiente,
    Leido,
    Indefinido;

    @JsonCreator
    public static EstadoLectura from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Indefinido;
        }

        try {
            return EstadoLectura.valueOf(value.trim());
        } catch (IllegalArgumentException e) {
            return Indefinido;
        }
    }
}
