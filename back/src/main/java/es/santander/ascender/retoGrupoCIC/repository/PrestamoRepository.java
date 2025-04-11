package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Buscar los préstamos vencidos
    List<Prestamo> findByFechaPrevistaDevolucionBeforeAndFechaDevolucionIsNull(LocalDate fechaActual);

    // Buscar los préstamos que no se han devuelto
    List<Prestamo> findByFechaDevolucionIsNull();

    // préstamos que no se han devuelto filtrados por persona
    List<Prestamo> findByPersonaIdAndFechaDevolucionIsNull(Long personaId);

    // préstamos que no se han devuelto filtrados por fecha
    List<Prestamo> findByFechaPrevistaDevolucionAndFechaDevolucionIsNull(LocalDate fechaPrevistaDevolucion);

    // préstamos que no se han devuelto filtrados por persona y fecha
    List<Prestamo> findByPersonaIdAndFechaDevolucionIsNullAndFechaPrevistaDevolucion(Long personaId,
            LocalDate fechaPrevistaDevolucion);

    @Query("SELECT p FROM Prestamo p WHERE p.item.id = :itemId ORDER BY p.fechaPrestamo DESC")
    List<Prestamo> findRecentPrestamosByItem(@Param("itemId") Long itemId, Pageable pageable);
            
            
}
