package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.TipoItem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {
    Optional<TipoItem> findByNombre(String nombre);
}
