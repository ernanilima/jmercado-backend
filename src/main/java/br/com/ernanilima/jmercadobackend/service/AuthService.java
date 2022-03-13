package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.dto.auth.LoginDto;
import br.com.ernanilima.jmercadobackend.dto.auth.TokenDto;

public interface AuthService {
    TokenDto login(LoginDto loginDto);
}
