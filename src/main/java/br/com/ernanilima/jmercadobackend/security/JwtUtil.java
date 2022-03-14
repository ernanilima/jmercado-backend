package br.com.ernanilima.jmercadobackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("classpath:jwt.properties")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretWord;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    /**
     * Criar um token
     * @param companyEin String
     * @param email String
     * @return String
     */
    public String generateToken(String companyEin, String email) {
        return JWT.create()
                .withClaim("companyEin", companyEin)
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secretWord.getBytes()));
    }

    /**
     * Verificar se o token eh valido
     * @param token String
     * @return boolean
     */
    public boolean isValidToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if (decodedJWT == null) return false;

        String companyEin = decodedJWT.getClaim("companyEin").asString();
        String email = decodedJWT.getSubject();
        Date expirationDate = decodedJWT.getExpiresAt();
        Date currentDate = new Date(System.currentTimeMillis());
        return (companyEin != null && email != null && expirationDate != null && currentDate.before(expirationDate));
    }

    /**
     * Decodificar o token
     * @param token String
     * @return DecodedJWT
     */
    private DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secretWord.getBytes()))
                    .build()
                    .verify(token);
        } catch (Exception e) { return null; }
    }

    /**
     * Recupera o e-mail do usuario e o cnpj da empresa que consta no token
     * e que foram usados na geracao do token
     * @param token String
     * @return String
     */
    public String getUserEmailAndParameter(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if (decodedJWT == null) return null;

        return String.format("%s%s%s", decodedJWT.getSubject().trim(), "-", decodedJWT.getClaim("companyEin").asString());
    }
}
