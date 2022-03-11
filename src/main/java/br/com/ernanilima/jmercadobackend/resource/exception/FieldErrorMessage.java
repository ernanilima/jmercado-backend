package br.com.ernanilima.jmercadobackend.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Mensagem de erro do campo
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldErrorMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}
