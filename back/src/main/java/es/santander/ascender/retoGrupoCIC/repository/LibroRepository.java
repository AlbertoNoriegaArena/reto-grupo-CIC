package es.santander.ascender.retoGrupoCIC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.retoGrupoCIC.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar libro por su itemId (asociación con Item)
    Optional<Libro> findByItemId(Long itemId);
}
