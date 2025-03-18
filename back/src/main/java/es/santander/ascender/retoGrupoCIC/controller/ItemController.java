package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Crear un nuevo ítem
    @PostMapping
    public ResponseEntity<Item> crear(@RequestBody Item item) {
        Item nuevoItem = itemService.createItem(item);
        return ResponseEntity.ok(nuevoItem);
    }

    // Obtener todos los ítems
    @GetMapping
    public List<Item> obtenerTodos() {
        return itemService.obtenerItems();
    }

    // Obtener un ítem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> obtenerPorId(@PathVariable Long id) {
        Optional<Item> item = itemService.obtenerItemPorId(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un ítem existente
    @PutMapping("/{id}")
    public ResponseEntity<Item> actualizar(@PathVariable Long id, @RequestBody Item item) {
        Item itemActualizado = itemService.updateItem(id, item);
        if (itemActualizado != null) {
            return ResponseEntity.ok(itemActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un ítem
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        String mensaje = itemService.deleteItem(id);
        if (mensaje.equals("Ítem eliminado con éxito")) {
            return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }
}
