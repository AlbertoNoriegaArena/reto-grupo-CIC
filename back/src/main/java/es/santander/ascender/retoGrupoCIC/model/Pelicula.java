package es.santander.ascender.retoGrupoCIC.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Pelicula {
    @Id
    private Long itemId;

    private String director;
    private Integer duracion; // Duración en minutos
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