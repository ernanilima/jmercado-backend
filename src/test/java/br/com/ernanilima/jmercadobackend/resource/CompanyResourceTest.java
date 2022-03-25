package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.service.CompanyService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static br.com.ernanilima.jmercadobackend.utils.I18n.QUANTITY_OF_ERRORS;
import static br.com.ernanilima.jmercadobackend.utils.I18n.getMessage;
import static java.text.MessageFormat.format;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// cria uma porta aleatoria para os testes, evita que use a porta padrao que eh usada pela aplicacao
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// quando usar '@Autowired MockMvc'
@AutoConfigureMockMvc
class CompanyResourceTest {

    @Autowired
    private CompanyResource companyResource;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyService companyService;

    @Test
    void doNotEnterCompanyWithEmptyFields() throws Exception {
        String body = """
                        {
                            "companyName": "",
                            "tradingName": "",
                            "ein": "",
                            "email": "",
                            "address": {
                                "zipCode": "",
                                "country": "",
                                "city": "",
                                "state": "",
                                "district": "",
                                "street": "",
                                "number": "",
                                "complement": ""
                            },
                            "user": {
                                "name": "",
                                "email": "",
                                "password": ""
                            }
                        }
                        """;
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(jsonPath("$.message", is(format(getMessage(QUANTITY_OF_ERRORS), 21))));
    }
}