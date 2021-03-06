package br.com.ernanilima.jmercadobackend.domain;

import br.com.ernanilima.jmercadobackend.domain.permission.Permission;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "`user`")
public class User implements IEntityLog, Serializable {
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
    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Company company;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "userpermission",
            joinColumns=@JoinColumn(name = "idUser")
    )
    @MapKeyColumn(name = "permission_key")
    @Column(name="role")
    private Map<Integer, String> permissions =  new HashMap<>();

    @EqualsAndHashCode.Exclude
    private Timestamp dateInsert;
    @EqualsAndHashCode.Exclude
    private Timestamp dateUpdate;
    @EqualsAndHashCode.Exclude
    private Timestamp dateDelete;

    public User() {
        // permissao de leitura
        this.setPermissions(List.of(Permissions.toEnum(Permissions.USER.getId())));
    }

    public User(UUID idUser, String name, String email, String password) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;

        // permissao de leitura
        this.setPermissions(List.of(Permissions.toEnum(Permissions.USER.getId())));
    }

    public Set<Permission> getPermissions() {
        // retorna as permissoes com base no codigo
        return permissions.keySet().stream().map(Permissions::toEnum).collect(Collectors.toSet());
    }

    public void setPermissions(List<Permission> permissions) {
        // adiciona o codigo da permissao
        this.permissions.putAll(new HashSet<>(permissions).stream().collect(Collectors.toMap(Permission::getId, Permission::getRole)));
    }
}
