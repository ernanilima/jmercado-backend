package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Address;
import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.Telephone;
import br.com.ernanilima.jmercadobackend.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID idCompany;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String companyName; // razao social

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String tradingName; // nome fantasia

    @NotEmpty(message = "{empty.field}")
    @Length(min = 14, max = 14, message = "{length.min.field}")
    @CNPJ(message = "{invalid.ein}")
    private String ein; // cnpj

    @NotEmpty(message = "{empty.field}")
    @Email(message = "{invalid.email}")
    private String email;

    @Valid
    private TelephoneDto telephone;

    @Valid
    @NotNull(message = "{empty.field}")
    private AddressDto address;

    @Valid
    @NotNull(message = "{empty.field}")
    private UserDto user;

    @Setter(AccessLevel.NONE)
    @JsonIgnoreProperties({"company", "password"})
    private List<UserDto> userList;

    public CompanyDto(Company company) {
        this.idCompany = company.getIdCompany();
        this.companyName = company.getCompanyName();
        this.tradingName = company.getTradingName();
        this.ein = company.getEin();
        this.email = company.getEmail();
        if (company.getTelephone() != null)
            this.telephone = new TelephoneDto(company.getTelephone());
        this.address = new AddressDto(company.getAddress());
        this.userList = company.getUserList().stream().map(UserDto::new).collect(Collectors.toList());
    }

    /**
     * Quando for inserir uma empresa, o usuario responsavel eh criado
     * Esse metodo evita que algumas validacoes sejam realizadas no dto de usuario
     * @param user UserDto
     */
    public void setUser(UserDto user) {
        Company company = new Company();
        company.setIdCompany(UUID.randomUUID());
        user.setCompany(company);
        user.setPermissions(new ArrayList<>());
        this.user = user;
    }

    /**
     * Converter o dto para um model
     * @return Company
     */
    public Company toModel() {
        Company company = new Company(this.idCompany, this.companyName, this.tradingName, this.ein, this.email);
        if (this.telephone != null) {
            Telephone telephone = this.telephone.toModel();
            telephone.setCompany(company);
            company.setTelephone(telephone);
        }

        Address address = this.address.toModel();
        address.setCompany(company);
        company.setAddress(address);

        User user = this.user.toModel();
        user.setCompany(company);
        company.setUserList(new ArrayList<>(Collections.singleton(user)));
        return company;
    }
}
