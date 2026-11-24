package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

// Enum para los ordenamientos de la tabla de usuarios del panel de administración
public enum OrdenTablaUsuario {
    ID,
    DENUNCIAS;

    public static OrdenTablaUsuario from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return ID;
        }

        try {
            return OrdenTablaUsuario.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ID;
        }
    }
}