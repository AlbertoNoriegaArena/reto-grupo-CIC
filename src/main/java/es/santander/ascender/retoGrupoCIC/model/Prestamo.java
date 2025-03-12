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
    private LocalDate fechaPrevista;
    private Boolean devolucion;

}
