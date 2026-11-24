package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

// Enum para los estados de las publiaciones

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Estado {
    disponible("Disponible"),
    adevolver("A devolver"),
    entregado("Entregado"),
    retirado("Retirado"),
    demorado("Demorado"),
    enrevision("En revisión"),
    antregar("A entregar"),
    solicitado("Solicitado"),
    indefinido("Indefinido");

    private final String nombre;

    Estado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @JsonCreator
    public static Estado from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return indefinido;
        }

        try {
            return Estado.valueOf(value.trim());
        } catch (IllegalArgumentException e) {
            return indefinido;
        }
    }
}
