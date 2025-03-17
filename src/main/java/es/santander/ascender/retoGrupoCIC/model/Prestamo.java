package es.santander.ascender.retoGrupoCIC.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Persona persona;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaPrevistaDevolucion;
    private Boolean devuelto = false;

    
    public Prestamo() {
    }


    public Prestamo(Long id, Item item, Persona persona, LocalDate fechaPrestamo, LocalDate fechaDevolucion,
            LocalDate fechaPrevistaDevolucion, Boolean devuelto) {
        this.id = id;
        this.item = item;
        this.persona = persona;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
        this.devuelto = devuelto;
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


    public Boolean getDevuelto() {
        return devuelto;
    }


    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
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
