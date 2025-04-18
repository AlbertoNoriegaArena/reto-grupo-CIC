package es.santander.ascender.retoGrupoCIC.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Musica {
    @Id
    @JsonProperty(access = Access.READ_ONLY)
    private Long itemId;

    @Size(max = 20, message = "El género no puede tener más de 20 caracteres")
    private String genero;
    @Size(max = 30, message = "El cantante no puede tener más de 30 caracteres")
    private String cantante;
    @Size(max = 30, message = "El album no puede tener más de 30 caracteres")
    private String album;
    private String duracion;

    @OneToOne
    @MapsId
    private Item item;

    public Musica() {
    }
   
    public Musica(Long itemId, String genero, String cantante, String album, String duracion) {
        this.itemId = itemId;
        this.genero = genero;
        this.cantante = cantante;
        this.album = album;
        this.duracion = duracion;
    }

    public Musica(Long itemId, String genero, String cantante, String album, String duracion, Item item) {
        this.itemId = itemId;
        this.genero = genero;
        this.cantante = cantante;
        this.album = album;
        this.duracion = duracion;
        this.item = item;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCantante() {
        return cantante;
    }

    public void setCantante(String cantante) {
        this.cantante = cantante;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
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
        Musica other = (Musica) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        return true;
    }

    
}