package es.santander.ascender.retoGrupoCIC.config;

public class PersonaObligatoriaException extends RuntimeException {

    public PersonaObligatoriaException() {
        super("La persona es obligatoria");
    }
}
