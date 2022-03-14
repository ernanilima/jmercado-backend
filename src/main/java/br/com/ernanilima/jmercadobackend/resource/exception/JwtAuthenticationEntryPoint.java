package br.com.ernanilima.jmercadobackend.resource.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    /**
     * Exibe um erro personalizado para token invalido
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param arg2 AuthenticationException
     */
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException arg2) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(), "Erro de autenticação", "Token inválido", req.getRequestURI());

        OutputStream outputStream = res.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, standardError);
        outputStream.flush();
    }
}
