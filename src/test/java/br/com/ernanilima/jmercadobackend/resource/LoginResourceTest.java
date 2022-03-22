package br.com.ernanilima.jmercadobackend.resource;


import br.com.ernanilima.jmercadobackend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.ernanilima.jmercadobackend.utils.I18n.*;
import static java.text.MessageFormat.format;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// cria uma porta aleatoria para os testes, evita que use a porta padrao que eh usada pela aplicacao
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// quando usar '@Autowired MockMvc'
@AutoConfigureMockMvc
class LoginResourceTest {

    @Autowired
    private LoginResource loginResource;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void doNotLoginWithEmptyFields() throws Exception {
        String body = """
                        {
                            "companyEin": "",
                            "email": "",
                            "password": ""
                        }
                        """;
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(jsonPath("$.message", is(format(getMessage(QUANTITY_OF_ERRORS), 5))))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'companyEin')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'password')]").exists());
    }

    @Test
    void doNotLoginWithIncompleteFields() throws Exception {
        String body = """
                        {
                            "companyEin": "1234567890123",
                            "email": "email.com",
                            "password": "12345"
                        }
                        """;
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(jsonPath("$.message", is(format(getMessage(QUANTITY_OF_ERRORS), 3))))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'companyEin')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'password')]").exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_MIN_FIELD)
                                .replace("{min}", "14") + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(INVALID_EMAIL) + "')]")
                                .exists())
                .andExpect(
                        jsonPath("$.errors[?(@.message == '" + getMessage(LENGTH_FIELD)
                                .replace("{min}", "6").replace("{max}", "15") + "')]")
                                .exists());
    }

    @Test
    void loginSuccessfully() throws Exception {
        String body = """
                        {
                            "companyEin": "12345678901234",
                            "email": "email@email.com",
                            "password": "123456"
                        }
                        """;
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}