package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import java.time.LocalDate;
import java.util.Set;

public record LibroSeed(
    String titulo,
    String descripcion,
    int paginas,
    String edicion,
    int calificacion,
    LocalDate fechaPublicacion,
    String urlFoto,
    double precio,
    Set<Categoria> categorias,
    String editorial,
    String autor
) {}