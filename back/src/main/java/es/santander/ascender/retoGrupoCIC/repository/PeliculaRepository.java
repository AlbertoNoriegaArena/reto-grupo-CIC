package es.santander.ascender.retoGrupoCIC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.retoGrupoCIC.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{

    // Buscar una película por su itemId (asociación con Item)
    Optional<Pelicula> findByItemId(Long itemId);
}
