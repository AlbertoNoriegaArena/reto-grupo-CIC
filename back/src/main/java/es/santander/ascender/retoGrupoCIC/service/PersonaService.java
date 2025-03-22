package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.retoGrupoCIC.config.PersonaAsociadaAPrestamoException;
import es.santander.ascender.retoGrupoCIC.config.PersonaNotFoundException;
import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.repository.PersonaRepository;
import es.santander.ascender.retoGrupoCIC.repository.PrestamoRepository;

@Service
@Transactional
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    // Crear personas
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    // Listar personas
    @Transactional(readOnly = true)
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Listar 1 persona por id
    @Transactional(readOnly = true)
    public Persona getPersonaById(Long id) {
        return personaRepository.findById(id).orElseThrow(() -> new PersonaNotFoundException(id));
    }

    // Actuizar persona
    public Persona updatePersona(Long id, Persona persona) {
        Optional<Persona> personaOptional = personaRepository.findById(id);

        if (!personaOptional.isPresent()) {
            throw new PersonaNotFoundException(id);
        }

        Persona personaExistente = personaOptional.get();

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

        return personaRepository.saveAndFlush(personaExistente);
    }

    // Eliminar personas
    public void deletePersona(Long id) {

        Optional<Persona> personaOpcional = personaRepository.findById(id);

        if (!personaRepository.existsById(id)) {
            throw new PersonaNotFoundException(id);
        }

        Persona persona = personaOpcional.get();

        // Verificar si la persona está o ha estado asociada a un préstamo
        if (prestamoRepository.existsByPersonaId(id)) {
            throw new PersonaAsociadaAPrestamoException(persona.getNombre());
        }
        personaRepository.deleteById(id);
    }

}
