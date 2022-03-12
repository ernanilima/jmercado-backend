package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.security.UserSpringSecurity;
import br.com.ernanilima.jmercadobackend.service.UserService;
import br.com.ernanilima.jmercadobackend.support.SupportUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private SupportUser supportUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.equals(supportUser.getEmail())) {
            // verifique se o usuário recebido é o usuário do suporte
            return new UserSpringSecurity(supportUser.getEmail(), supportUser.getPassword(), supportUser.getPermissions());
        }

        User user = userService.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);

        return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getPermissions());
    }
}
