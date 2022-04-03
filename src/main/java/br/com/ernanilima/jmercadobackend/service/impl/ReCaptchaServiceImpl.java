package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.security.ReCaptchaGoogleResponse;
import br.com.ernanilima.jmercadobackend.service.ReCaptchaService;
import br.com.ernanilima.jmercadobackend.service.exception.ReCaptchaInvalidException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.util.regex.Pattern;

/**
 * Referencia <https://www.baeldung.com/spring-security-registration-captcha>
 */
@PropertySource("classpath:private_jmercado.properties")
@Component
public class ReCaptchaServiceImpl implements ReCaptchaService {

    @Autowired
    private RestOperations restTemplate;

    @Value("${recaptcha.secret.key}")
    private String recaptchaSecretKey;
    @Value("${recaptcha.server.url}")
    private String recaptchaServerURL;

    @SneakyThrows
    @Override
    public void processResponse(String response) {

        responseSanityCheck(response);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecretKey);
        map.add("response", response);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ReCaptchaGoogleResponse reCaptchaGoogleResponse = restTemplate.postForObject(
                recaptchaServerURL, request, ReCaptchaGoogleResponse.class
        );

        if (reCaptchaGoogleResponse == null || !reCaptchaGoogleResponse.isSuccess())
            throw new ReCaptchaInvalidException("ReCaptcha não foi validado com sucesso");
    }

    private void responseSanityCheck(String response) {
        Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
        if (StringUtils.hasLength(response) && !RESPONSE_PATTERN.matcher(response).matches())
            throw new ReCaptchaInvalidException("O ReCaptcha contém caracteres inválidos");
    }
}
