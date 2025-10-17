package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

public enum Avatar {
    ALICIA("alicia.png"),
    BRUJA("bruja.png"),
    CAPERUCITAROJA("caperucitaRoja.png"),
    CENICIENTA("cenicienta.png"),
    DETECTIVE("detective.png"),
    DOROTHY("dorothy.png"),
    DRACULA("dracula.png"),
    FRANKENSTEIN("frankenstein.png"),
    GANDALF("gandalf.png"),
    HARRYPOTTER("harryPotter.png"),
    HOMBREDEHOJALATA("homreDeHojalata.png"),
    KATNISS("katniss.png"),
    PINOCHO("pinocho.png"),
    RAPUNZEL("rapunzel.png"),
    REINADECORAZONES("reinaDeCorazones.png");

    private final String url;

    Avatar(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}