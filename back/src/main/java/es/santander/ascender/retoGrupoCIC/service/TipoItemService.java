package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoItemService {

    private TipoItemRepository tipoItemRepository;

    @Autowired
    public TipoItemService(TipoItemRepository tipoItemRepository) {
        this.tipoItemRepository = tipoItemRepository;
    }

    public TipoItem createTipoItem(TipoItem tipoItem) {
        return tipoItemRepository.save(tipoItem);
    }

    @Transactional(readOnly = true)
    public List<TipoItem> obtenerTipoItems() {
        return tipoItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TipoItem> obtenerTipoItemPorId(Long id) {
        return tipoItemRepository.findById(id);
    }

    public TipoItem updateTipoItem(Long id, TipoItem tipoItemActualizado) {
        if (tipoItemRepository.existsById(id)) {
            tipoItemActualizado.setId(id);
            return tipoItemRepository.save(tipoItemActualizado);
        }
        return null;
    }

    public String deleteTipoItem(Long id) {
        Optional<TipoItem> tipoItem = tipoItemRepository.findById(id);
        if (tipoItem.isPresent()) {
            tipoItemRepository.deleteById(id);
            return "TipoItem eliminado con éxito";
        }
        return "El TipoItem no existe";
    }
}
