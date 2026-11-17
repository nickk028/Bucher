package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

// Enum para los ordenamientos del listado de publicaciones sociales
public enum OrdenPublicacionSocial {
    RECIENTES,
    ANTIGUAS,
    POPULARES;

    public static OrdenPublicacionSocial from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return RECIENTES;
        }

        try {
            return OrdenPublicacionSocial.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return RECIENTES;
        }
    }
}