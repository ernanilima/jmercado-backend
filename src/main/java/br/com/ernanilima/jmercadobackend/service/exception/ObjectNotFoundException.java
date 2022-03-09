package br.com.ernanilima.jmercadobackend.service.exception;

import java.io.Serial;

/**
 * Objeto nao encontrado
 */
public class ObjectNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
