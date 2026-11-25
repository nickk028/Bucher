package ar.edu.huergo.vectorial.calidad.bucher.entity.book;

// Enum para las categorías de los libros
public enum Categoria {
    aventura("Aventura"),
    cienciaficcion("Ciencia Ficción"),
    fantastico("Fantástico"),
    suspenso("Suspenso"),
    policial("Policial"),
    realismomagico("Realismo Mágico"),
    romance("Romance"),
    terror("Terror"),
    historico("Histórico"),
    psicologia("Psicología"),
    drama("Drama"),
    humor("Humor"),
    infantil("Infantil");

    private final String nombre;

    Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}