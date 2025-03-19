package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.repository.PersonaRepository;

@Service
@Transactional
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    //Crear personas
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    //Listar personas
    @Transactional(readOnly = true)
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    //Listar 1 persona por id
    @Transactional(readOnly = true)
    public Persona getPersonaById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    //Actuizar persona
    public Persona updatePersona(Long id, Persona persona) {
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if (personaOptional.isPresent()) {
            Persona personaExistente = personaOptional.get();
            // Actualizar solo los campos que se han modificado
            if (persona.getNombre() != null) {
                personaExistente.setNombre(persona.getNombre());
            }
            if (persona.getDireccion() != null) {
                personaExistente.setDireccion(persona.getDireccion());
            }
            if (persona.getEmail() != null) {
                personaExistente.setEmail(persona.getEmail());
            }
            if (persona.getTelefono() != null) {
                personaExistente.setTelefono(persona.getTelefono());
            }
            return personaRepository.save(personaExistente);
        }
        throw new RuntimeException("No pude encontrar la persona con id " + id);
    }

    
    //Eliminar personas
    public void deletePersona(Long id) {
        personaRepository.deleteById(id);
    }
    
}
