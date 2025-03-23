package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemPrestadoException;
import es.santander.ascender.retoGrupoCIC.config.PersonaNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.PrestamoBorradoException;
import es.santander.ascender.retoGrupoCIC.config.PrestamoNotFoundException;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;
import es.santander.ascender.retoGrupoCIC.service.PrestamoService;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> createPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo newPrestamo = prestamoService.createPrestamo(prestamo);
            return new ResponseEntity<>(newPrestamo, HttpStatus.CREATED);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PersonaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Long id) {
        Optional<Prestamo> prestamo = prestamoService.getPrestamoById(id);
        return prestamo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrestamo(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        try {
            Prestamo prestamoActualizado = prestamoService.updatePrestamo(id, prestamo);
            return ResponseEntity.ok(prestamoActualizado);
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PersonaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ItemPrestadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (PrestamoBorradoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrestamo(@PathVariable Long id) {
        try {
            prestamoService.deletePrestamo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PrestamoBorradoException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<?> devolverPrestamo(@PathVariable Long id) {
        try {
            Prestamo prestamoDevuelto = prestamoService.devolverPrestamo(id);
            return ResponseEntity.ok(prestamoDevuelto);
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la devolución del préstamo.");
        }
    }

    @GetMapping("/personas/{personaId}")
    public ResponseEntity<?> getPrestamosByPersonaId(@PathVariable Long personaId) {
        try {
            List<Prestamo> prestamos = prestamoService.getPrestamosByPersonaId(personaId);
            return ResponseEntity.ok(prestamos);
        } catch (PersonaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los prestamos");
        }
    }

    // Endpoint para obtener los préstamos vencidos
    @GetMapping("/vencidos")
    public ResponseEntity<List<Prestamo>> obtenerPrestamosVencidos() {
        List<Prestamo> prestamosVencidos = prestamoService.listarPrestamosVencidos();
        return new ResponseEntity<>(prestamosVencidos, HttpStatus.OK);
    }
}
