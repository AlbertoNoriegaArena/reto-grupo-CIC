package es.santander.ascender.retoGrupoCIC.dto;

public class TipoItemDTO {
    private String nombre;

    public TipoItemDTO() {
    }

    public TipoItemDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
