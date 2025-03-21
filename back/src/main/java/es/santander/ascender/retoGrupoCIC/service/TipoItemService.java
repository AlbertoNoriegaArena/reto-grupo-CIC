package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.dto.TipoItemDTO;
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

    public TipoItem createTipoItem(TipoItemDTO tipoItemDTO) {
        // Validar que el nombre no esté vacío
        if (tipoItemDTO.getNombre() == null || tipoItemDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de ítem no puede estar vacío");
        }
        // Validar que el nombre no exista ya
        if (tipoItemRepository.findByNombre(tipoItemDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un tipo de ítem con ese nombre");
        }
        TipoItem tipoItem = new TipoItem();
        tipoItem.setNombre(tipoItemDTO.getNombre());
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

    public TipoItem updateTipoItem(Long id, TipoItemDTO tipoItemDTO) {
        Optional<TipoItem> tipoItemOptional = tipoItemRepository.findById(id);
        if (tipoItemOptional.isPresent()) {
            TipoItem tipoItemExistente = tipoItemOptional.get();
            // Validar que el nombre no esté vacío
            if (tipoItemDTO.getNombre() == null || tipoItemDTO.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del tipo de ítem no puede estar vacío");
            }
            // Validar que el nombre no exista ya
            Optional<TipoItem> tipoItemConMismoNombre = tipoItemRepository.findByNombre(tipoItemDTO.getNombre());
            if (tipoItemConMismoNombre.isPresent() && !tipoItemConMismoNombre.get().getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un tipo de ítem con ese nombre");
            }
            tipoItemExistente.setNombre(tipoItemDTO.getNombre());
            return tipoItemRepository.save(tipoItemExistente);
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
