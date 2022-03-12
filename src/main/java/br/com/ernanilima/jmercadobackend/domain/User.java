package br.com.ernanilima.jmercadobackend.domain;

import br.com.ernanilima.jmercadobackend.domain.permission.Permission;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "`user`")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID idUser;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permissions")
    private Set<Integer> permissions =  new HashSet<>();

    public User() {
        // permissao de leitura
        this.setPermissions(List.of(Permissions.toEnum(Permissions.FIND.getId())));
    }

    public User(UUID idUser, String name, String email, String password) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;

        // permissao de leitura
        this.setPermissions(List.of(Permissions.toEnum(Permissions.FIND.getId())));
    }

    public Set<Permission> getPermissions() {
        // retorna as permissoes com base no codigo
        return permissions.stream().map(Permissions::toEnum).collect(Collectors.toSet());
    }

    public void setPermissions(List<Permission> permissions) {
        // adiciona o codigo da permissao
        this.permissions.addAll(permissions.stream().map(Permission::getId).collect(Collectors.toSet()));
    }
}
