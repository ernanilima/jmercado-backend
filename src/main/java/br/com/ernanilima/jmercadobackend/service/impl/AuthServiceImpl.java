package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.dto.auth.LoginDto;
import br.com.ernanilima.jmercadobackend.dto.auth.TokenDto;
import br.com.ernanilima.jmercadobackend.security.JwtUtil;
import br.com.ernanilima.jmercadobackend.service.AuthService;
import br.com.ernanilima.jmercadobackend.service.exception.JwtAuthenticationException;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletResponse res;

    @Override
    @Transactional(readOnly = true)
    public TokenDto login(LoginDto loginDto) {
        try {
            // obitem os dados para realizar o login
            UsernamePasswordAuthenticationToken authToken = getAuthentication(loginDto);
            // usa o "UserDetailsService" para verificar se o login eh valido
            authenticationManager.authenticate(authToken);
            // se a autenticação for realizada
            String token = ("Bearer " + jwtUtil.generateToken(loginDto.getCompanyEin(), loginDto.getEmail()));

            // envia o token no cabecalho
            res.addHeader("Authorization", token);
            res.addHeader("access-control-expose-headers", "Authorization");

            // envia o token no dto
            return new TokenDto(loginDto. getCompanyEin(), loginDto.getEmail(), token);
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException(I18n.getMessage(I18n.BAD_CREDENTIALS));
        }
    }

    /**
     * Constroi o usuario e os dados para login
     * @param loginDto LoginDto
     * @return UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(LoginDto loginDto) {
        // constroi uma string com o e-mail do login e o cnpj da empresa, dados recebidos para realizar login
        String emailAndParameter = String.format("%s%s%s", loginDto.getEmail().trim(), "-", loginDto.getCompanyEin());
        return new UsernamePasswordAuthenticationToken(emailAndParameter, loginDto.getPassword());
    }
}
