package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Address;
import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.Telephone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String companyName; // razao social

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String tradingName; // nome fantasia

    @NotEmpty(message = "{empty.field}")
    @Length(min = 14, max = 14, message = "{length.ein.field}")
    private String ein; // cnpj

    @NotEmpty(message = "{empty.field}")
    @Email(message = "{email.field}")
    private String email;

    private Telephone telephone;

    @Valid
    private AddressDto address;

    public CompanyDto(Company company) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
        this.tradingName = company.getTradingName();
        this.ein = company.getEin();
        this.email = company.getEmail();
        this.telephone = company.getTelephone();
        this.address = new AddressDto(company.getAddress());
    }

    /**
     * Converter um dto para o model
     * @return Company
     */
    public Company toModel() {
        Company company = new Company(this.id, this.companyName, this.tradingName, this.ein, this.email);
        company.setTelephone(this.telephone);
        Address address = this.address.toModel();
        address.setCompany(company);
        company.setAddress(address);
        return company;
    }
}
