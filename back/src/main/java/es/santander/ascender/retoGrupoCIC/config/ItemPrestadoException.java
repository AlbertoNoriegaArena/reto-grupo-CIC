package es.santander.ascender.retoGrupoCIC.config;

public class ItemPrestadoException extends RuntimeException {
    private Long itemId;
    private String nombre;

    public ItemPrestadoException(){
    }

    public ItemPrestadoException( Long itemId) {
        super("El ítem " + itemId + " está actualmente prestado");
        this.itemId = itemId;
    }

    public ItemPrestadoException( String nombre) {
        super("El ítem " + nombre + " está actualmente prestado");
        this.nombre = nombre;
    }

    public ItemPrestadoException(Long itemId, String nombre) {
        super("El ítem " + nombre + " con ID: " + itemId + " está actualmente prestado");
        this.itemId = itemId;
        this.nombre = nombre;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getNombre() {
        return nombre;
    }

    
}