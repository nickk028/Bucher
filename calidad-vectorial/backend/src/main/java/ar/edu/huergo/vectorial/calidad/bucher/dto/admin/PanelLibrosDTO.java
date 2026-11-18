package ar.edu.huergo.vectorial.calidad.bucher.dto.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class PanelLibrosDTO {

    // Cantidad de libros registrados
    private long librosRegistrados;

    // Cantidad de libros independientes (de la editorial propia de los usuarios)
    private long librosIndependientes;

    // Cantidad de libros de cada categoría
    private List<CantidadCategoriaDTO> categoriasLibros = new ArrayList<>();

    // Porcentaje de las 5 categorías más guardadas y de las restantes agrupadas en "Otras"
    private List<PorcentajeCategoriaDTO> categoriasMasGuardadas = new ArrayList<>();

    // Los 5 libros más guardados
    private List<RankingLibroDTO> librosMasGuardados = new ArrayList<>();

    // Porcentaje de libros guardados por al menos un usuario (0 a 1)
    private double porcentajeLibrosGuardados;

    // Porcentaje de libros nombrados en al menos un posteo (0 a 1)
    private double porcentajeLibrosNombradosEnPosteos;

    // Porcentaje de libros con al menos una publicación (0 a 1)
    private double porcentajeLibrosConPublicaciones;
}