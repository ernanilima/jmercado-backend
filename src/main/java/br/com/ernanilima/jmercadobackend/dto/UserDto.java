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

    private UUID idUser;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String name;

    @NotEmpty(message = "{empty.field}")
    @Email(message = "{email.field}")
    private String email;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 6, max = 15, message = "{length.field}")
    private String password; // nao exibe no get

    @JsonIgnoreProperties({"userList", "telephone", "address", "dateInsert", "dateUpdate"})
    private Company company;

    @NotNull(message = "{empty.field}")
    private List<Permission> permissions;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @NotEmpty(message = "{empty.field}")
    @Length(min = 36, max = 36, message = "{length.min.field}")
    private String idCompany;

    public UserDto(User user) {
        this.idUser = user.getIdUser();
        this.name = user.getName();
        this.email = user.getEmail();
        this.company = user.getCompany();
        this.permissions = user.getPermissions().stream().map(p -> Permissions.toEnum(p.getId())).collect(Collectors.toList());
    }

    public void setCompany(Company company) {
        this.idCompany = company.getIdCompany().toString();
        this.company = company;
    }

    /**
     * Converter o dto para um model
     * @return User
     */
    public User toModel() {
        User user = new User(this.idUser, this.name, this.email, this.password);
        user.setCompany(this.company);
        user.setPermissions(this.permissions.stream().map(p -> Permissions.toEnum(p.getId())).collect(Collectors.toList()));
        return user;
    }
}
