package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;
// Enum para los estados de las publiaciones

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Estado {
    Disponible,
    Entrega_pendiente,
    Prestado,
    Devolucion_pendiente,
    No_disponible,
    Indefinido;

    @JsonCreator
    public static Estado from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Indefinido;
        }

        try {
            return Estado.valueOf(value.trim());
        } catch (IllegalArgumentException e) {
            return Indefinido;
        }
    }
}