package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.dto.TipoItemFormatoDTO;
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

@RestController
@RequestMapping("/api/TipoItemFormatos")
public class TipoItemFormatoController {

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

     @PostMapping
    public ResponseEntity<?> createTipoItemFormato(@RequestBody TipoItemFormatoDTO dto) {
        if (dto.getTipoItemId() == null || dto.getFormatoId() == null) {
            return new ResponseEntity<>("Los campos tipoItemId y formatoId son obligatorios.", HttpStatus.BAD_REQUEST);
        }

        try {
            TipoItemFormato savedTipoItemFormato = tipoItemFormatoService.createTipoItemFormato(dto.getTipoItemId(), dto.getFormatoId());
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
