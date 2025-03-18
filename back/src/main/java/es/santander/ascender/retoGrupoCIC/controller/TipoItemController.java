package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.service.TipoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipoItems")
public class TipoItemController {

    private final TipoItemService tipoItemService;

    @Autowired
    public TipoItemController(TipoItemService tipoItemService) {
        this.tipoItemService = tipoItemService;
    }

    @PostMapping
    public ResponseEntity<TipoItem> createTipoItem(@RequestBody TipoItem tipoItem) {
        TipoItem nuevoTipoItem = tipoItemService.createTipoItem(tipoItem);
        return new ResponseEntity<>(nuevoTipoItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TipoItem>> obtenerTipoItems() {
        List<TipoItem> tipoItems = tipoItemService.obtenerTipoItems();
        return ResponseEntity.ok(tipoItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoItem> obtenerTipoItemPorId(@PathVariable Long id) {
        Optional<TipoItem> tipoItem = tipoItemService.obtenerTipoItemPorId(id);
        return tipoItem.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoItem> updateTipoItem(@PathVariable Long id, @RequestBody TipoItem tipoItem) {
        TipoItem tipoItemActualizado = tipoItemService.updateTipoItem(id, tipoItem);
        if (tipoItemActualizado != null) {
            return ResponseEntity.ok(tipoItemActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTipoItem(@PathVariable Long id) {
        String resultado = tipoItemService.deleteTipoItem(id);
        if (resultado.equals("TipoItem eliminado con éxito")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
    }
}
