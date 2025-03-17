package es.santander.ascender.retoGrupoCIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.santander.ascender.retoGrupoCIC.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}