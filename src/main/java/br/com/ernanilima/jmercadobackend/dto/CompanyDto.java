package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Address;
import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.Telephone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

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
    private AddressDto address;

    public CompanyDto(Company company) {
        this.idCompany = company.getIdCompany();
        this.companyName = company.getCompanyName();
        this.tradingName = company.getTradingName();
        this.ein = company.getEin();
        this.email = company.getEmail();
        if (company.getTelephone() != null)
            this.telephone = new TelephoneDto(company.getTelephone());
        if (company.getAddress() != null)
            this.address = new AddressDto(company.getAddress());
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
        if (this.address != null) {
            Address address = this.address.toModel();
            address.setCompany(company);
            company.setAddress(address);
        }
        return company;
    }
}
