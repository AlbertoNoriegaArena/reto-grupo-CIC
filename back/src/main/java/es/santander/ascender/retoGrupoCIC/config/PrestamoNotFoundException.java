package es.santander.ascender.retoGrupoCIC.config;

public class PrestamoNotFoundException extends RuntimeException {
    private Long prestamoId;

    public PrestamoNotFoundException(Long prestamoId) {
        super("No se encontró el prestamo con ID: " + prestamoId);
        this.prestamoId = prestamoId;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }
}