package es.santander.ascender.retoGrupoCIC.config;

public class FormatoNotFoundException extends RuntimeException {
    private Long formatoId;

    public FormatoNotFoundException(Long formatoId) {
        super("No se encontró el formato con ID: " + formatoId);
        this.formatoId = formatoId;
    }
    public FormatoNotFoundException(Long id, String mensaje) {
        super("Formato con id " + id + ": " + mensaje);
    }

    public Long getFormatoId() {
        return formatoId;
    }
}