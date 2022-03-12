package br.com.ernanilima.jmercadobackend.config;

import br.com.ernanilima.jmercadobackend.security.JwtAuthenticationFilter;
import br.com.ernanilima.jmercadobackend.security.JwtAuthorizationFilter;
import br.com.ernanilima.jmercadobackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    // endpoints publicos
    private static final String[] PUBLIC_PATHS = { "/h2-console/**" };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            // se estiver no perfil "test", garante que o h2-console será exibido
            http.headers().frameOptions().disable();
        }

        // habilita a consiguracao cors no metodo "corsConfigurationSource"
        http.cors().and()
                // desabilita o csrf, geralmente usado por quem armazena sessão
                .csrf().disable();

        http.authorizeRequests()
                // permite tudo na variavel 'PUBLIC_PATHS'
                .antMatchers(PUBLIC_PATHS).permitAll()
                // solicita autenticação para o resto
                .anyRequest().authenticated();

        // garante que nenhuma sessão de usuário será criada
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, jwtUtil));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // define o service e o metodo de encoder para a senha
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // configurações básicas de liberação do cors
        // habilitar solicitação de várias fontes
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
