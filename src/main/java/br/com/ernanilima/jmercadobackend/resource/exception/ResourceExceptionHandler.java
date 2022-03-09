package br.com.ernanilima.jmercadobackend.resource.exception;

import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Manipulador de erros.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Exibe um erro persolanilado para busca nao encontrada
     * Esse erro eh exibido automaticamente para a classe {@link ObjectNotFoundException}
     * @param e ObjectNotFoundException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
}
