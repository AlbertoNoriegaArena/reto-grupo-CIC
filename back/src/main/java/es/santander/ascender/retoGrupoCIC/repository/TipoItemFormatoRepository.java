package es.santander.ascender.retoGrupoCIC.repository;

import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormatoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TipoItemFormatoRepository extends JpaRepository<TipoItemFormato, TipoItemFormatoId> {
    Set<TipoItemFormato> findByTipoItem(TipoItem tipoItem);

    List<TipoItemFormato> findByFormatoId(Long formatoId);
}
