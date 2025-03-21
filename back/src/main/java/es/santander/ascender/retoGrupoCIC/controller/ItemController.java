package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> crear(@RequestBody Item item) {
        try {
            Item nuevoItem = itemService.createItem(item);
            return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Item item) {
        try {
            Item itemActualizado = itemService.updateItem(id, item);
            if (itemActualizado != null) {
                return ResponseEntity.ok(itemActualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar un ítem
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        String mensaje = itemService.deleteItem(id);
        if (mensaje.equals("Ítem eliminado con éxito")) {
            return ResponseEntity.ok(mensaje);
        } else if (mensaje.equals("No se puede eliminar el ítem porque está actualmente prestado")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) EstadoItem estado,
            @RequestParam(required = false) String ubicacion) {

        try {
            List<Item> items = itemService.searchItems(titulo, tipo, estado, ubicacion);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disponibles")
    public List<Item> getAvailableItems() {
        return itemService.getAvailableItems();
    }

    @GetMapping("/prestados")
    public List<Item> getBorrowedItems() {
        return itemService.getBorrowedItems();
    }
}
