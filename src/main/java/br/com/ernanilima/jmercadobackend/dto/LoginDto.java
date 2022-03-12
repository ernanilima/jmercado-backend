package br.com.ernanilima.jmercadobackend.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class LoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // dados solicitados no login
    private String email;
    private String password;
}
