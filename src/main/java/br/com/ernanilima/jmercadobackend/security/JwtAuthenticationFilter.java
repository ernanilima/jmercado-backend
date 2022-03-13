package br.com.ernanilima.jmercadobackend.security;

//@AllArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//    private JwtUtil jwtUtil;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
//        // obitem os dados para realizar o login
//        UsernamePasswordAuthenticationToken authToken = getAuthentication(req);
//        // usa o "UserDetailsService" para verificar se o login eh valido
//        return authenticationManager.authenticate(authToken);
//    }
//
//    /**
//     * Constroi o usuario e os dados para login
//     * @param req HttpServletRequest
//     * @return UsernamePasswordAuthenticationToken
//     */
//    @SneakyThrows
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
//        // captura os dados recebidos para realizar o login e atribui eles ao 'LoginDto'
//        LoginDto loginDto = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);
//        // constroi uma string com o e-mail do login e o cnpj da empresa, dados recebidos para realizar login
//        String emailAndParameter = String.format("%s%s%s", loginDto.getEmail().trim(), "-", loginDto.getCompanyEin());
//        return new UsernamePasswordAuthenticationToken(emailAndParameter, loginDto.getPassword());
//    }
//
//    /**
//     * Executa apenas se a autenticação for realizada
//     * @param req HttpServletRequest
//     * @param res HttpServletResponse
//     * @param chain FilterChain
//     * @param authResult Authentication
//     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) {
//        String companyEin = ((UserSpringSecurity) authResult.getPrincipal()).getCompanyEin();
//        String email = ((UserSpringSecurity) authResult.getPrincipal()).getUsername();
//        String token = jwtUtil.generateToken(companyEin, email);
//        // envia o token no cabecalho
//        res.addHeader("Authorization", "Bearer " + token);
//        res.addHeader("access-control-expose-headers", "Authorization");
//    }
//}
