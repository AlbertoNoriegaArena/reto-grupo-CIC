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

}