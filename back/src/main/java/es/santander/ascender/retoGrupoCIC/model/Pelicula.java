package es.santander.ascender.retoGrupoCIC.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Pelicula {
    @Id
    @JsonProperty(access = Access.READ_ONLY)
    private Long itemId;

    @Size(max = 40, message = "El nombre del director no puede tener más de 40 caracteres")
    private String director;
    @Min(value = 0, message = "La duración debe ser mayor a 0 minutos")
    private Integer duracion; // Duración en minutos
    @Size(max = 30, message = "El género no puede tener más de 30 caracteres")
    private String genero;
    private LocalDate fechaEstreno;

    @OneToOne
    @MapsId
    private Item item;

    public Pelicula() {
    }

    public Pelicula(Long itemId, String director, Integer duracion, String genero, LocalDate fechaEstreno) {
        this.itemId = itemId;
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
    }

    public Pelicula(Long itemId, String director, Integer duracion, String genero, LocalDate fechaEstreno, Item item) {
        this.itemId = itemId;
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
        this.item = item;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pelicula other = (Pelicula) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        return true;
    }

    
}