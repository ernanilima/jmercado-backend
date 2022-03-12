package br.com.ernanilima.jmercadobackend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // recuperar o cabeçalho no parâmetro
        String headerAuthorization = request.getHeader("Authorization");

        // o cabeçalho existe e começa com o valor 'Bearer '
        if (headerAuthorization != null && headerAuthorization.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthorization(headerAuthorization.substring(7));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthorization(String token) {
        if (jwtUtil.isValidToken(token)) {
            // busca o e-mail do usuario e o cnpj da empresa que esta no token
            String emailAndParameter = jwtUtil.getUserEmailAndParameter(token);
            UserDetails user = userDetailsService.loadUserByUsername(emailAndParameter); // buscar o usuario no service
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
