package es.santander.ascender.retoGrupoCIC.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class TipoItemFormato {
    @EmbeddedId
    private TipoItemFormatoId id;

    @ManyToOne
    @MapsId("tipoItem")
    private TipoItem tipoItem;

    @ManyToOne
    @MapsId("formato")
    private Formato formato;

    public TipoItemFormatoId getId() {
        return id;
    }

    public void setId(TipoItemFormatoId id) {
        this.id = id;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
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
        TipoItemFormato other = (TipoItemFormato) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    

}
