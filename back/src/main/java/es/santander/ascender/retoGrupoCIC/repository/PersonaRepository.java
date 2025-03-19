package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.Persona;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
