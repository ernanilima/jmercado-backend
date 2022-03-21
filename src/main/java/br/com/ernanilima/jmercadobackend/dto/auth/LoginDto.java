package br.com.ernanilima.jmercadobackend.dto.auth;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Getter
public class LoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 14, max = 14, message = "{length.min.field}")
    private String companyEin; // cnpj da empresa

    @NotEmpty(message = "{empty.field}")
    @Email(message = "{invalid.email}")
    private String email;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 6, max = 15, message = "{length.field}")
    private String password;
}
