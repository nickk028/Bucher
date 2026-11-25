package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

// Enum para los ordenamientos de la tabla de publicaciones sociales del panel de administración
public enum OrdenTablaPublicacionSocial {
    RECIENTES,
    DENUNCIAS,
    USUARIO;

    public static OrdenTablaPublicacionSocial from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return RECIENTES;
        }

        try {
            return OrdenTablaPublicacionSocial.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return RECIENTES;
        }
    }
}