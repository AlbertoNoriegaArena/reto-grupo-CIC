package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.service.TipoItemFormatoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/TipoItemFormatos")
public class TipoItemFormatoController {

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @PostMapping
    public ResponseEntity<?> createTipoItemFormato(@RequestBody Map<String, Map<String, Long>> requestBody) {
        // Verificar si el cuerpo de la solicitud es válido
        if (requestBody == null || !requestBody.containsKey("tipoItem") || !requestBody.containsKey("formato")) {
            return new ResponseEntity<>("Cuerpo de la solicitud inválido. Debe contener los campos 'tipoItem' y 'formato'.", HttpStatus.BAD_REQUEST);
        }

        // Extraer los IDs del cuerpo de la solicitud
        Map<String, Long> tipoItemMap = requestBody.get("tipoItem");
        Map<String, Long> formatoMap = requestBody.get("formato");

        // Verificar si los IDs son válidos
        if (tipoItemMap == null || !tipoItemMap.containsKey("id") || formatoMap == null || !formatoMap.containsKey("id")) {
            return new ResponseEntity<>("Cuerpo de la solicitud inválido. Debe contener los campos 'id' dentro de 'tipoItem' y 'formato'.", HttpStatus.BAD_REQUEST);
        }

        Long tipoItemId = tipoItemMap.get("id");
        Long formatoId = formatoMap.get("id");

        try {
            // Crear y guardar la asociación
            TipoItemFormato savedTipoItemFormato = tipoItemFormatoService.createTipoItemFormato(tipoItemId, formatoId);
            return new ResponseEntity<>(savedTipoItemFormato, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoItemFormato>> getAllTipoItemFormatos() {
        List<TipoItemFormato> tipoItemFormatos = tipoItemFormatoService.obtenerTipoItemFormatos();
        return new ResponseEntity<>(tipoItemFormatos, HttpStatus.OK);
    }

    @DeleteMapping("/{tipoItemId}/{formatoId}")
    public ResponseEntity<Void> deleteTipoItemFormato(@PathVariable Long tipoItemId, @PathVariable Long formatoId) {
        String result = tipoItemFormatoService.deleteTipoItemFormato(tipoItemId, formatoId);
        if (result.equals("TipoItemFormato eliminado con éxito")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
