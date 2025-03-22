package es.santander.ascender.retoGrupoCIC.config;

public class PersonaAsociadaAPrestamoException extends RuntimeException {

    private Long itemId;
    private String nombre;

    public PersonaAsociadaAPrestamoException() {
    }

    public PersonaAsociadaAPrestamoException(String nombre) {
        super("No puedes borrar a " + nombre + ", está asociado a un préstamo");
        this.nombre = nombre;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getNombre() {
        return nombre;
    }

}
