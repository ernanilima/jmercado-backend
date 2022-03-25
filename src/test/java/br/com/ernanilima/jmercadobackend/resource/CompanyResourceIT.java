package br.com.ernanilima.jmercadobackend.resource;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

// cria uma porta aleatoria para os testes, evita que use a porta padrao que eh usada pela aplicacao
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// quando usar '@Autowired MockMvc'
@AutoConfigureMockMvc
class CompanyResourceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<Void> supportHeader;
    private HttpEntity<Void> userHeader;

    @BeforeEach
    public void configSupportHeader() {
        String s = "{\"companyEin\": \"00000000000191\", \"email\": \"support@ernanilima.com.br\", \"password\": \"127386\"}";
        HttpHeaders headers = restTemplate.postForEntity("/auth/login", s, String.class).getHeaders();
        this.supportHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configUserHeader() {
        String s = "{\"companyEin\": \"00000000000191\", \"email\": \"email@email.com\", \"password\": \"123123\"}";
        HttpHeaders headers = restTemplate.postForEntity("/auth/login", s, String.class).getHeaders();
        this.userHeader = new HttpEntity<>(headers);
    }

    @Test
    void findAllok() {
        System.out.println(supportHeader);
        ResponseEntity<String> response = restTemplate.exchange("/empresa", HttpMethod.GET, supportHeader, String.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), CoreMatchers.is(200));
    }
}