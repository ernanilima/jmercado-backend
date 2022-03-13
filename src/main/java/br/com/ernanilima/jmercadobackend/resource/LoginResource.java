package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.dto.auth.LoginDto;
import br.com.ernanilima.jmercadobackend.dto.auth.TokenDto;
import br.com.ernanilima.jmercadobackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class LoginResource {

    @Autowired
    private AuthService authService;

    /**
     * Realizar login
     * @param loginDto LoginDto
     * @return ResponseEntity<TokenDto>
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok().body(tokenDto);
    }
}
