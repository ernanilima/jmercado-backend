package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.domain.permission.Permission;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String name;

    @NotEmpty(message = "{empty.field}")
    @Email(message = "{email.field}")
    private String email;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 6, max = 15, message = "{length.field}")
    private String password; // nao exibe no get

    @JsonIgnoreProperties({"userList", "telephone", "address"})
    private Company company;

    @NotNull(message = "{empty.field}")
    private List<Permission> permissions;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @NotEmpty(message = "{empty.field}")
    private UUID idCompany;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.company = user.getCompany();
        this.permissions = user.getPermissions().stream().map(e -> Permissions.toEnum(e.getId())).collect(Collectors.toList());
    }

    public void setCompany(Company company) {
        this.idCompany = company.getId();
        this.company = company;
    }

    /**
     * Converter o dto para um model
     * @return User
     */
    public User toModel() {
        User user = new User(this.id, this.name, this.email, this.password);
        user.setCompany(this.company);
        user.setPermissions(this.permissions);
        return user;
    }
}
