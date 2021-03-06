package br.com.ernanilima.jmercadobackend.resource.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Exibe um erro personalizado para token sem autorizacao
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param accessDeniedException AccessDeniedException
     */
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        StandardError standardError = new StandardError(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(), "Erro de autorização", "Não autorizado", req.getRequestURI());

        OutputStream outputStream = res.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, standardError);
        outputStream.flush();
    }
}
