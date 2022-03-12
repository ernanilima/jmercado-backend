package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.security.UserSpringSecurity;
import br.com.ernanilima.jmercadobackend.service.UserService;
import br.com.ernanilima.jmercadobackend.support.SupportUser;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private SupportUser supportUser;

    @Override
    public UserDetails loadUserByUsername(String emailAndParameter) throws UsernameNotFoundException {
        // separa os valores para realizar a validacao de login
        String[] values = StringUtils.split(emailAndParameter, "-");

        if (values == null || values.length != 2)
            throw new UsernameNotFoundException(I18n.getMessage(I18n.INVALID_LOGIN));

        String email = values[0];
        String companyEin = values[1];

        // se for um usuario de suporte
        if (email.equals(supportUser.getEmail()))
            return new UserSpringSecurity("companyEin", supportUser.getEmail(), supportUser.getPassword(), supportUser.getPermissions());

        User user = userService.findByEmail(email, companyEin);
        if (user == null)
            throw new UsernameNotFoundException(I18n.getMessage(I18n.INVALID_LOGIN));

        return new UserSpringSecurity(user.getCompany().getEin(), user.getEmail(), user.getPassword(), user.getPermissions());
    }
}
