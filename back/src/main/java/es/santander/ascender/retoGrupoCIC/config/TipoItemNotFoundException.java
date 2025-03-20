package es.santander.ascender.retoGrupoCIC.config;

public class TipoItemNotFoundException extends RuntimeException {
    private Long tipoItemId;

    public TipoItemNotFoundException(Long tipoItemId) {
        super("No se encontró el tipo de ítem con ID: " + tipoItemId);
        this.tipoItemId = tipoItemId;
    }

    public Long getTipoItemId() {
        return tipoItemId;
    }
}