package es.santander.ascender.retoGrupoCIC.config;

public class PersonaNotFoundException extends RuntimeException {
    private Long personaId;

    public PersonaNotFoundException(Long personaId) {
        super("No se encontró la persona con ID: " + personaId);
        this.personaId = personaId;
    }

    public Long getPersonaId() {
        return personaId;
    }
}