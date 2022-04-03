package br.com.ernanilima.jmercadobackend.service.exception;

import java.io.Serial;

/**
 * ReCaptcha invalido ou nao autorizado
 */
public class ReCaptchaInvalidException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ReCaptchaInvalidException(String message) {
        super(message);
    }
}
