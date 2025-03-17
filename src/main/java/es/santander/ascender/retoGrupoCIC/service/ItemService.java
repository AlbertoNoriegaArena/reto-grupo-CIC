package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<Item> obtenerItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Item> obtenerItemPorId(Long id) {
        return itemRepository.findById(id);
    }

    public Item updateItem(Long id, Item itemActualizado) {
        if (itemRepository.existsById(id)) {
            itemActualizado.setId(id);
            return itemRepository.save(itemActualizado);
        }
        return null;
    }

    public String deleteItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.deleteById(id);
            return "Item eliminado con éxito";
        }
        return "El item no existe";
    }
}
