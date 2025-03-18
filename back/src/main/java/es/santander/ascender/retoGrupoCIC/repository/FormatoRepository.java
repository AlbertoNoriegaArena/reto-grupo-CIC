package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.Formato;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FormatoRepository extends JpaRepository<Formato, Long> {
    Optional<Formato> findByNombre(String nombre);
}
