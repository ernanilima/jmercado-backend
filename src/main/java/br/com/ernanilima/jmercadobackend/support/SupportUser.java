package br.com.ernanilima.jmercadobackend.support;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.domain.permission.Permission;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Component
public class SupportUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id = Permissions.SUPPORT.getId();
    private final String description = Permissions.SUPPORT.getDescription();
    private final String email = "support@ernanilima.com.br";
    private final Set<Permission> permissions = new HashSet<>(Arrays.stream(Permissions.values()).map(p -> Permissions.toEnum(p.getId())).collect(Collectors.toSet()));

    public User getSupportUser() {
        User user = new User(UUID.randomUUID(), getDescription(), getEmail(), null);
        user.setPermissions(permissions.stream().toList());
        return user;
    }

    public String getPassword() {
        return SupportPassword.getHashingPassword();
    }
}
