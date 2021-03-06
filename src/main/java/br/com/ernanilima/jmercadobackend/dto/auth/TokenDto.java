package br.com.ernanilima.jmercadobackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TokenDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String companyEin; // cnpj da empresa
    private String email;
    private String token;
}
