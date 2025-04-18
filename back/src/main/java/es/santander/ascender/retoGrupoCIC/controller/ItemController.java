package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.dto.ItemDTO;
import es.santander.ascender.retoGrupoCIC.dto.ItemCompletoDTO;
import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.service.ItemService;
import jakarta.validation.Valid;

import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.TipoItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNoValidoException;
import es.santander.ascender.retoGrupoCIC.config.CustomValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*" )
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Crear un nuevo ítem
    @PostMapping
    public ResponseEntity<?> crearItem(@Valid @RequestBody ItemDTO itemDTO) {
        try {
            Item nuevoItem = itemService.createItem(itemDTO); // El itemDTO tiene los ids de tipo y formato
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

    @GetMapping("/completos")
    public List<ItemCompletoDTO> obtenerTodosCompletos() { 
        return itemService.obtenerItemsCompletos(); 
    }

    // Obtener un ítem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> obtenerPorId(@PathVariable Long id) {
        Optional<Item> item = itemService.obtenerItemPorId(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un ítem existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO) { 
        try {
            Item itemActualizado = itemService.updateItem(id, itemDTO);
            return ResponseEntity.ok(itemActualizado);
        } catch (ItemNotFoundException | TipoItemNotFoundException | FormatoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 si no se encuentra Item, Tipo o Formato
        } catch (FormatoNoValidoException | IllegalArgumentException | CustomValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 para otros errores de validación
        } catch (Exception e) { 
            return new ResponseEntity<>("Error interno al actualizar el item.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un ítem
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok(""); // 200 OK con un cuerpo vacío
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
