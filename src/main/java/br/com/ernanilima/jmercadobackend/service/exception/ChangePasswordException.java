package br.com.ernanilima.jmercadobackend.service.exception;

import java.io.Serial;

/**
 * Alteracao de senha
 */
public class ChangePasswordException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ChangePasswordException(String message) {
        super(message);
    }
}
