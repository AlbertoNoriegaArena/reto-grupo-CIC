package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.dto.FormatoDTO;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.service.FormatoService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemService;
import jakarta.validation.Valid;

import es.santander.ascender.retoGrupoCIC.config.FormatoNotFoundException;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*" )
@RestController
@RequestMapping("/api/formatos")
public class FormatoController {

    private FormatoService formatoService;
    private TipoItemService tipoItemService;

    @Autowired
    public FormatoController(FormatoService formatoService, TipoItemService tipoItemService) {
        this.formatoService = formatoService;
        this.tipoItemService = tipoItemService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FormatoDTO formatoDTO) {
        try {
            Formato nuevoFormato = formatoService.createFormato(formatoDTO);
            return new ResponseEntity<>(nuevoFormato, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Formato>> obtenerFormatos() {
        List<Formato> formatos = formatoService.obtenerFormatos();
        return ResponseEntity.ok(formatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formato> obtenerFormatoPorId(@PathVariable Long id) {
        Optional<Formato> formato = formatoService.obtenerFormatoPorId(id);
        return formato.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody FormatoDTO formatoDTO) {
        try {
            Formato formatoActualizado = formatoService.updateFormato(id, formatoDTO);
            if (formatoActualizado != null) {
                return ResponseEntity.ok(formatoActualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormato(@PathVariable Long id) { 
        try {
            formatoService.deleteFormato(id);
            // Si la eliminación es exitosa, devolver 204 No Content
            return ResponseEntity.noContent().build();
        } catch (FormatoNotFoundException e) {
            // Si el formato no existe, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obtener los formatos válidos para un TipoItem
    @GetMapping("/tipoItem/{tipoItemId}")
    public ResponseEntity<List<Formato>> obtenerFormatosPorTipoItem(@PathVariable Long tipoItemId) {
        Optional<TipoItem> tipoItemOptional = tipoItemService.obtenerTipoItemPorId(tipoItemId);
        if (tipoItemOptional.isPresent()) {
            TipoItem tipoItem = tipoItemOptional.get();
            List<Formato> formatos = formatoService.obtenerFormatosPorTipoItem(tipoItem);
            return ResponseEntity.ok(formatos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
