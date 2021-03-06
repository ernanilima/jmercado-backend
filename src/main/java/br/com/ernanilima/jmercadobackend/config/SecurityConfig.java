package br.com.ernanilima.jmercadobackend.config;

import br.com.ernanilima.jmercadobackend.resource.exception.JwtAccessDeniedHandler;
import br.com.ernanilima.jmercadobackend.resource.exception.JwtAuthenticationEntryPoint;
import br.com.ernanilima.jmercadobackend.security.JwtAuthorizationFilter;
import br.com.ernanilima.jmercadobackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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

/**
 * WebSecurityConfigurerAdapter foi 'descontinuado', no link demonstra como alterar
 * {@link <a href="https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter"></a>}
 * As solucoes de alteracoes ainda estao incompletas, implementar a alteracao no futuro
 */
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
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // endpoints publicos
    private static final String[] PUBLIC_PATHS = {"/", "/auth/**", "/endereco/**", "/h2-console/**"};
    private static final String[] PUBLIC_POST = {"/empresa"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // se estiver no perfil "test", garante que o h2-console ser?? exibido
        if (Arrays.asList(environment.getActiveProfiles()).contains("test"))
            http.headers().frameOptions().disable();

        // habilita a consiguracao cors no metodo "corsConfigurationSource"
        http.cors().and()
                // desabilita o csrf, geralmente usado por quem armazena sess??o
                .csrf().disable();

        http.authorizeRequests()
                // permite tudo na variavel 'PUBLIC_PATHS'
                .antMatchers(PUBLIC_PATHS).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_POST).permitAll()
                // solicita autentica????o para o resto
                .anyRequest().authenticated();

        // garante que nenhuma sess??o de usu??rio ser?? criada
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // adiciona um filtro de autorizacao
        http.addFilter(new JwtAuthorizationFilter(authenticationManagerBean(), userDetailsService, jwtUtil));
        // classe para exibir erro para nao autorizado
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                // exibir erro de token invalido
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // configura????es b??sicas de libera????o do cors
        // habilitar solicita????o de v??rias fontes
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Bean para transformar a senha em sha 512
     * @return BCryptPasswordEncoder
     */
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para gerenciar a validacao
     * @return AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
