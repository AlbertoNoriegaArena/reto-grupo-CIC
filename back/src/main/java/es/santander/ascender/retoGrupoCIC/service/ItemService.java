package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @Autowired
    private FormatoService formatoService;

    @Autowired
    private TipoItemService tipoItemService;

    public Item createItem(Item item) {
        // Validar que el tipo cuadra con el formato introducido
        validateTipoItemFormato(item.getTipo(), item.getFormato());

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
        Optional<Item> itemOpcional = itemRepository.findById(id);
        if (itemOpcional.isPresent()) {
            Item itemExistente = itemOpcional.get();

            // Primero validamos que el tipo y el formato cumplan
            validateTipoItemFormato(itemActualizado.getTipo(), itemActualizado.getFormato());

            // Actualizar los campos menos el estado
            itemExistente.setNombre(itemActualizado.getNombre());
            itemExistente.setTipo(itemActualizado.getTipo());
            itemExistente.setFormato(itemActualizado.getFormato());
            itemExistente.setUbicacion(itemActualizado.getUbicacion());
            itemExistente.setFecha(itemActualizado.getFecha());

            return itemRepository.save(itemExistente);
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

    // método que comprueba que la relación entre tipo y formato sea correcta
    private void validateTipoItemFormato(TipoItem tipoItem, Formato formato) {
        if (tipoItem == null || formato == null) {
            throw new IllegalArgumentException("El tipo del Item y el formato son obligatorios");
        }
        // Comprobar que el tipoItem existe
        Optional<TipoItem> tipoItemOptional = tipoItemService.obtenerTipoItemPorId(tipoItem.getId());
        if (!tipoItemOptional.isPresent()) {
            throw new IllegalArgumentException("El tipoItem no existe");
        }
        // comprobar que el formato exista
        Optional<Formato> formatoOptional = formatoService.obtenerFormatoPorId(formato.getId());
        if (!formatoOptional.isPresent()) {
            throw new IllegalArgumentException("El formato no existe");
        }

        Set<TipoItemFormato> tipoItemFormatos = tipoItemFormatoService.obtenerTipoItemFormatosPorTipoItem(tipoItem);
        boolean isValid = tipoItemFormatos.stream()
                .anyMatch(tif -> tif.getFormato().equals(formato));

        if (!isValid) {
            throw new IllegalArgumentException(
                    "El formato no es válido para el tipo seleccionado. Por favor, selecciona valores validos");
        }
    }
}
