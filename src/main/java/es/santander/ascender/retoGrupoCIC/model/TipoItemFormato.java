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

}
