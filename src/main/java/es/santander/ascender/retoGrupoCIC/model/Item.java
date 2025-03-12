package es.santander.ascender.retoGrupoCIC.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    private TipoItem tipo;

    @ManyToOne
    private Formato formato;

    private String ubicacion;
    
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoItem estado = EstadoItem.DISPONIBLE;
 
}

