package br.com.ernanilima.jmercadobackend.support;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.domain.permission.Permission;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Component
public class SupportUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id = Permissions.SUPPORT.getId();
    private final String description = Permissions.SUPPORT.getDescription();
    private final String email = "support@ernanilima.com.br";
    private final Set<Permission> permissions = new HashSet<>(Collections.singletonList(Permissions.toEnum(getId())));

    public User getSupportUser() {
        User user = new User(null, getDescription(), getEmail(), null);
        user.setPermissions(List.of(Permissions.toEnum(getId())));
        return user;
    }

    public String getPassword() {
        return SupportPassword.getPassword();
    }
}
