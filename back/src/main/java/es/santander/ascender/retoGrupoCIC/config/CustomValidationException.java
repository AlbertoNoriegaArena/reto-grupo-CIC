package es.santander.ascender.retoGrupoCIC.config;

import jakarta.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;

import es.santander.ascender.retoGrupoCIC.model.Item;

import java.util.HashMap;

public class CustomValidationException extends RuntimeException {

    private final Map<String, String> violations;

    public CustomValidationException(Set<ConstraintViolation<Item>> violations2) {
        this.violations = new HashMap<>();

        // Convertimos las violaciones a un mapa de claves (campo) y mensajes de error
        for (ConstraintViolation<?> violation : violations2) {
            violations.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }

    public Map<String, String> getViolations() {
        return violations;
    }
}
