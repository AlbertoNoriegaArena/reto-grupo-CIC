package es.santander.ascender.retoGrupoCIC.config;

public class ItemObligatorioException extends RuntimeException {

    public ItemObligatorioException() {
        super("El item es obligatorio");
    }
}
