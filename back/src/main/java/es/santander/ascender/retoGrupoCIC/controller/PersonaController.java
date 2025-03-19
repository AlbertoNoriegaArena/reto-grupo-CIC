package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.service.PersonaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;
    
    @PostMapping
    public Persona createPersona(Persona persona) {
        return personaService.createPersona(persona);
    }

    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }

    @GetMapping("/{id}")
    public Persona getPersonaById(Long id) {
        return personaService.getPersonaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        try {
            Persona personaActualizada = personaService.updatePersona(id, persona);
            return ResponseEntity.ok(personaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public void deletePersona(Long id) {
        personaService.deletePersona(id);
    }
}
