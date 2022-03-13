package br.com.ernanilima.jmercadobackend.resource.exception;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Exibir multiplos erros
 */
public class ValidationError extends StandardError {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FieldErrorMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldErrorMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String messagem) {
        errors.add(new FieldErrorMessage(fieldName, messagem));
    }
}
