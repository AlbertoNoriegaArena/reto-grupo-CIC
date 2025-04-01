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
public class Libro {
    @Id
    @JsonProperty(access = Access.READ_ONLY)
    private Long itemId;

    @Size(max=30 , message = "El autor no puede superar los 30 caracteres")
    private String autor;
    private String isbn;
    @Size(max=30 , message = "La editorial no puede superar los 30 caracteres")
    private String editorial;
    @Min(value = 0, message = "El número de páginas debe ser mayor a 0")
    private Integer numeroPaginas;
    private LocalDate fechaPublicacion;

    @OneToOne
    @MapsId
    private Item item;

    public Libro() {
    }

    public Libro(Long itemId, String isbn, String editorial, Integer numeroPaginas) {
        this.itemId = itemId;
        this.isbn = isbn;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
    }

    public Libro(Long itemId, String isbn, String editorial, Integer numeroPaginas, Item item) {
        this.itemId = itemId;
        this.isbn = isbn;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.item = item;
    }

    public Libro(Long itemId, String autor, String isbn, String editorial, Integer numeroPaginas,
            LocalDate fechaPublicacion, Item item) {
        this.itemId = itemId;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.fechaPublicacion = fechaPublicacion;
        this.item = item;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
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
        Libro other = (Libro) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        return true;
    }

    
}