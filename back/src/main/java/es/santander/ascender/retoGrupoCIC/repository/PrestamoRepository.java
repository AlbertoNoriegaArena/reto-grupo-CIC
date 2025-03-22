package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    // Prestamos activos de una persona
    List<Prestamo> findByPersonaAndBorradoFalse(Persona persona); 

    // prestamos NO borrados
    List<Prestamo> findByBorradoFalse(); 
}
