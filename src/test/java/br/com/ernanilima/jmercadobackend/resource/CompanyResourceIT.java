package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.support.SupportPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// cria uma porta aleatoria para os testes, evita que use a porta padrao que eh usada pela aplicacao
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// quando usar '@Autowired MockMvc'
@AutoConfigureMockMvc
class CompanyResourceIT {

    @Autowired
    private MockMvc mockMvc;

    private String tokenSupport;

    @BeforeEach
    public void configTokenSupport() throws Exception {
        String s = "{\"companyEin\": \"00000000000191\", \"email\": \"support@ernanilima.com.br\", \"password\": \"" + SupportPassword.getBasicPassword() + "\"}";
        tokenSupport = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andReturn().getResponse().getHeader("Authorization");
    }

    @Test
    void successInFindAllCompanies() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenSupport))
                .andExpect(status().isOk());
    }
}