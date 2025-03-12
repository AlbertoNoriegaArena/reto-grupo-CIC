package es.santander.ascender.retoGrupoCIC.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class TipoItemFormatoId implements Serializable {
    private Long tipoItem;
    private Long formato;

    public TipoItemFormatoId() {}

    public TipoItemFormatoId(Long tipoItem, Long formato) {
        this.tipoItem = tipoItem;
        this.formato = formato;
    }

    // Getters y Setters
    public Long getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(Long tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Long getFormato() {
        return formato;
    }

    public void setFormato(Long formato) {
        this.formato = formato;
    }

    // Métodos equals() y hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoItemFormatoId that = (TipoItemFormatoId) o;
        return Objects.equals(tipoItem, that.tipoItem) &&
               Objects.equals(formato, that.formato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoItem, formato);
    }
}

