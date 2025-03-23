package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    // Prestamos activos de una persona
    List<Prestamo> findByPersonaAndBorradoFalse(Persona persona); 

    // prestamos NO borrados
    List<Prestamo> findByBorradoFalse(); 

    // Devuelve un préstamo si existe una relación con el ítem
    Optional<Prestamo> findByItem(Item item);  

    // Devuelve un préstamo si existe una relación con la persona
    boolean existsByPersonaId(Long id);  

    boolean existsByItem(Item item);
}
