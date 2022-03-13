package br.com.ernanilima.jmercadobackend.resource.exception;

import br.com.ernanilima.jmercadobackend.service.exception.DataIntegrityException;
import br.com.ernanilima.jmercadobackend.service.exception.JwtAuthenticationException;
import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * Manipulador de erros.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Exibe uma lista erros, um erro personalizado para cada validacao que nao foi atendida
     * @param e MethodArgumentNotValidException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest r) {
        String message = "Quantidade de erro(s): " + e.getErrorCount();
        ValidationError validarErro = new ValidationError(
                Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", message, r.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validarErro.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validarErro);
    }

    /**
     * Exibe um erro persolanizado para integridade de dados,
     * atualmente usado quando inserir ou atualizar objeto
     * @param e DataIntegrityException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                Instant.now(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    /**
     * Exibe um erro persolanizado para busca nao encontrada
     * Esse erro eh exibido automaticamente para a classe {@link ObjectNotFoundException}
     * @param e ObjectNotFoundException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                Instant.now(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    /**
     * Exibe um erro personalizado para login nao realizado
     * @param e JwtAuthenticationException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<StandardError> jwtAuthentication(JwtAuthenticationException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                Instant.now(), HttpStatus.UNAUTHORIZED.value(), "Erro de autenticação", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError);
    }
}
