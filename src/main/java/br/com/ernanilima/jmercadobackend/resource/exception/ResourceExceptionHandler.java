package br.com.ernanilima.jmercadobackend.resource.exception;

import br.com.ernanilima.jmercadobackend.service.exception.*;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        String message = MessageFormat.format(I18n.getMessage(I18n.QUANTITY_OF_ERRORS), e.getErrorCount());
        ValidationError validarErro = new ValidationError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", message, r.getRequestURI());

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
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), r.getRequestURI());
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
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    /**
     * Quando o metodo recebido nao for suportado, exemplo: recebido DELETE quando deveria ser GET
     * @param e HttpRequestMethodNotSupportedException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest r) {
        String message = MessageFormat.format(I18n.getMessage(I18n.METHOD_NOT_SUPPORTED), e.getMethod());
        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.BAD_REQUEST.value(), "Método não permitido", message, r.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    /**
     * Endpoint nao encontrado
     * @param e NoHandlerFoundException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardError> noHandlerFound(NoHandlerFoundException e, HttpServletRequest r) {
        String message = I18n.getMessage(I18n.ENDPOINT_NOT_FOUND);
        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.NOT_FOUND.value(), "Endpoint indisponível", message, r.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    /**
     * Impossivel converter o valor recebido, exemplo: recebido 'abc' quando deveria ser '123'
     * @param e MethodArgumentTypeMismatchException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> argumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest r) {
        String message = I18n.getMessage(I18n.INVALID_VALUE_TYPE);
        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.BAD_REQUEST.value(), "Valor inválido", message, r.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
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
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(), "Erro de autenticação", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError);
    }

    /**
     * Senha do usuario nao pode ser alterada
     * @param e ChangePasswordException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<StandardError> changePassword(ChangePasswordException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(), "Erro de autorização", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError);
    }

    /**
     * ReCaptcha invalido
     * @param e ReCaptchaInvalidException
     * @param r HttpServletRequest
     * @return ResponseEntity<StandardError>
     */
    @ExceptionHandler(ReCaptchaInvalidException.class)
    public ResponseEntity<StandardError> reCaptchaInvalid(ReCaptchaInvalidException e, HttpServletRequest r) {
        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro com recaptcha", e.getMessage(), r.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standardError);
    }
}
