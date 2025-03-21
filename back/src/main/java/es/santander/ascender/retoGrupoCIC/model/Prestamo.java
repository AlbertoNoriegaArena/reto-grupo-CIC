package es.santander.ascender.retoGrupoCIC.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    // @JsonProperty("itemId"): Permite que itemId aparezca en el JSON de salida
    @JsonProperty("itemId")
    public Long getItemId() {
        return item != null ? item.getId() : null;
    }

    @Transient
    @JsonProperty("itemId")
    // Si en el JSON de entrada se recibe un itemId, se crea una instancia de Item y
    // se asigna el id, sin cargar toda la entidad desde la base de datos.
    public void setItemId(Long itemId) {
        if (itemId != null) {
            this.item = new Item(); // Creamos solo una referencia con el ID
            this.item.setId(itemId);
        }
    }

    @ManyToOne
    @JsonProperty(access = Access.READ_ONLY)
    private Item item;

    @ManyToOne
    private Persona persona;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaPrevistaDevolucion;

    public Prestamo() {
    }

    public Prestamo(Long id, Item item, Persona persona, LocalDate fechaPrestamo, LocalDate fechaDevolucion,
            LocalDate fechaPrevistaDevolucion) {
        this.id = id;
        this.item = item;
        this.persona = persona;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    public void setFechaPrevistaDevolucion(LocalDate fechaPrevistaDevolucion) {
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Prestamo other = (Prestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
