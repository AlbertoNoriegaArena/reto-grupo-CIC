package es.santander.ascender.retoGrupoCIC.controller;

import es.santander.ascender.retoGrupoCIC.dto.TipoItemFormatoDTO;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.service.TipoItemFormatoService;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid; 
import java.util.List;
import java.util.Map; 

@CrossOrigin(origins = "*", allowedHeaders = "*" )
@RestController
@RequestMapping("/api/TipoItemFormatos")
public class TipoItemFormatoController {

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @PostMapping
    public ResponseEntity<?> createTipoItemFormato(@Valid @RequestBody TipoItemFormatoDTO dto) {
        try {
            TipoItemFormato savedTipoItemFormato = tipoItemFormatoService.createTipoItemFormato(dto.getTipoItemId(),
                    dto.getFormatoId());
            // Devolver el objeto creado con estado 201 Created
            return new ResponseEntity<>(savedTipoItemFormato, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si la excepción es por "no encontrado", devolvemos 404. Si es por "ya existe", 409 Conflict.
            if (e.getMessage().contains("no encontrado")) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
            } else if (e.getMessage().contains("ya existe")) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
            } else {
                // Otro tipo de IllegalArgumentException
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
            }
        } catch (Exception e) {
             // Captura genérica para otros errores inesperados
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Ocurrió un error inesperado al crear la relación."));
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoItemFormato>> getAllTipoItemFormatos() {
        List<TipoItemFormato> tipoItemFormatos = tipoItemFormatoService.obtenerTipoItemFormatos();
        // Devolver la lista con estado 200 OK (implícito con ResponseEntity.ok)
        return ResponseEntity.ok(tipoItemFormatos);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> deleteTipoItemFormato( 
            @RequestParam Long tipoItemId,
            @RequestParam Long formatoId) {
        try {
            tipoItemFormatoService.eliminarRelacion(tipoItemId, formatoId);
            // Si la eliminación es exitosa, devolver 204 No Content
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // Si la relación no existe, devolver 404 Not Found con el mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/porFormato")
    public ResponseEntity<?> getRelacionesPorFormato(@RequestParam Long formatoId) {
         try {
            List<TipoItemFormato> relaciones = tipoItemFormatoService.obtenerTipoItemFormatosPorFormatoId(formatoId);
            return ResponseEntity.ok(relaciones);
        } catch (IllegalArgumentException e) {
            // Si el formato no existe (según la validación añadida en el servicio)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
             // Captura genérica
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al obtener relaciones por formato."));
        }
    }
}
