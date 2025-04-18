package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.service.PersonaService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*" )
@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @PostMapping
    public Persona createPersona(@Valid @RequestBody Persona persona) {
        return personaService.createPersona(persona);
    }

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }

    @GetMapping("/{id}")
    public Persona getPersonaById(@PathVariable Long id) {
        return personaService.getPersonaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @Valid @RequestBody Persona persona) {

        Persona personaActualizada = personaService.updatePersona(id, persona);
        return ResponseEntity.ok(personaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        try {
            personaService.deletePersona(id);
            return ResponseEntity.ok(""); // 200 OK con un cuerpo vacío
        } catch ( IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
