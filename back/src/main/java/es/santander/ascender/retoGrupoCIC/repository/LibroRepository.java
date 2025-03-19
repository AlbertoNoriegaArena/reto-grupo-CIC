package es.santander.ascender.retoGrupoCIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.retoGrupoCIC.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
