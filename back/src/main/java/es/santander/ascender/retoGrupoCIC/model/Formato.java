package es.santander.ascender.retoGrupoCIC.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Formato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 30, message = "El nombre del formato no puede tener más de 30 caracteres")
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "formato")
    private Set<Item> items;

    @OneToMany(mappedBy = "formato")
    private Set<TipoItemFormato> tipoItemFormatos;

    public Formato() {
    }

    public Formato(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Formato(Long id, String nombre, Set<Item> items) {
        this.id = id;
        this.nombre = nombre;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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
        Formato other = (Formato) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
