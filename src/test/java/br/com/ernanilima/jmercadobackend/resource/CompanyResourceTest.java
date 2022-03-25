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

import static br.com.ernanilima.jmercadobackend.utils.I18n.*;
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

    @Test
    void doNotEnterCompanyWithIncompleteFields() throws Exception {
        String body = """
                        {
                            "companyName": "Ep ltda",
                            "tradingName": "Empresa",
                            "ein": "1234567890",
                            "email": "email@.com",
                            "address": {
                                "zipCode": "1234567",
                                "country": "Brasil",
                                "city": "Curitiba",
                                "state": "Paraná",
                                "district": "Centro",
                                "street": "Rua Principal",
                                "number": "S/N",
                                "complement": "Apto"
                            },
                            "user": {
                                "name": "ErnaniLima",
                                "email": "email1@.com",
                                "password": "12312"
                            }
                        }
                        """;
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(jsonPath("$.message", is(format(getMessage(QUANTITY_OF_ERRORS), 8))))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'companyName')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'tradingName')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'ein')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'address.zipCode')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'user.email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'user.password')]").exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_FIELD)
                                .replace("{min}", "8").replace("{max}", "50") + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_MIN_FIELD)
                                .replace("{min}", "14") + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(INVALID_EIN) + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(INVALID_EMAIL) + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_MIN_FIELD)
                                .replace("{min}", "8") + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_FIELD)
                                .replace("{min}", "6").replace("{max}", "15") + "')]")
                                .exists());
    }
}