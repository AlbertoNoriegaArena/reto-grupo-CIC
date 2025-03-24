package es.santander.ascender.retoGrupoCIC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.retoGrupoCIC.model.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    // Buscar una musica por su itemId (asociación con Item)
    Optional<Musica> findByItemId(Long itemId);
}
