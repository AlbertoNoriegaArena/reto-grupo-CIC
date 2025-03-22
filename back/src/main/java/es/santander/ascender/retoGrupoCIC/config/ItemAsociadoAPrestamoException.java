package es.santander.ascender.retoGrupoCIC.config;

public class ItemAsociadoAPrestamoException extends RuntimeException {

    private Long itemId;
    private String nombre;

    public ItemAsociadoAPrestamoException() {
    }

    public ItemAsociadoAPrestamoException(String nombre) {
        super("No puedes borrar el ítem " + nombre + " está asociado a un préstamo");
        this.nombre = nombre;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getNombre() {
        return nombre;
    }

}
