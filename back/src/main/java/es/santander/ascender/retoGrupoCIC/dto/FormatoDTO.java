package es.santander.ascender.retoGrupoCIC.dto;

public class FormatoDTO {
    private String nombre;

    public FormatoDTO() {
    }

    public FormatoDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
