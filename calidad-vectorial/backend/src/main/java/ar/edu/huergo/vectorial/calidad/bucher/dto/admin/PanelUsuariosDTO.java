package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PanelUsuariosDTO {

    // Cantidad de usuarios totales (admins incluidos)
    private long usuariosTotales;

    // Cantidad de usuarios creados en el año actual
    private long usuariosNuevos;

    // Cantidad de usuarios creados en cada mes del año actual
    private List<CantidadMesDTO> usuariosNuevosPorMes = new ArrayList<>();

    // Porcentaje de usuarios activos sobre el total de usuarios (0 a 1)
    private double porcentajeUsuariosActivos;

    // Porcentaje de usuarios inactivos (un mes sin ingresar) sobre el total de usuarios (0 a 1)
    private double porcentajeUsuariosInactivos;

    // Porcentaje de usuarios con préstamos activos sobre el total de usuarios (0 a 1)
    private double porcentajeUsuariosConPrestamosActivos;

    // Porcentaje de usuarios con publicaciones sobre el total de usuarios (0 a 1)
    private double porcentajeUsuariosConPublicaciones;

    // Porcentaje de usuarios con posteos sobre el total de usuarios (0 a 1)
    private double porcentajeUsuariosConPosteos;
}