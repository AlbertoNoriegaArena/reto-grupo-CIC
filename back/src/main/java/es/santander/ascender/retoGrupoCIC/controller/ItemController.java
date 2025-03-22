package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.dto.ItemDTO;
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
    public ResponseEntity<?> crearItem(@RequestBody ItemDTO itemDTO) {
        try {
            Item nuevoItem = itemService.createItem(itemDTO);
            return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item item) {
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
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Ítem borrado con éxito");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) EstadoItem estado,
            @RequestParam(required = false) String ubicacion) {

        try {
            List<Item> items = itemService.searchItems(nombre, tipo, estado, ubicacion);
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
