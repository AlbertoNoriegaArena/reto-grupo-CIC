package es.santander.ascender.retoGrupoCIC.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Musica {
    @Id
    private Long itemId;

    private String isbn;
    private String editorial;
    private Integer numeroPaginas;

    @OneToOne
    @MapsId
    private Item item;

}