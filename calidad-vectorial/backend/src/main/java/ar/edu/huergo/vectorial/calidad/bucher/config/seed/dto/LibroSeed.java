package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import java.math.BigDecimal;
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
    BigDecimal precio,
    Set<Categoria> categorias,
    String editorial,
    String autor
) {}