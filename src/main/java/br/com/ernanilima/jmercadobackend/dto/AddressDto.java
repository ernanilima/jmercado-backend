package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID idAddress;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 8, message = "{zipcode.field}")
    private String zipCode; // cep

    @NotEmpty(message = "{empty.field}")
    private String country; // pais

    @NotEmpty(message = "{empty.field}")
    private String city; // cidade

    @NotEmpty(message = "{empty.field}")
    private String state; // estado

    @NotEmpty(message = "{empty.field}")
    private String district; // bairro

    @NotEmpty(message = "{empty.field}")
    private String street; // rua

    @NotEmpty(message = "{empty.field}")
    private String number; // numero

    private String complement; // complemento

    public AddressDto(Address address) {
        this.idAddress = address.getIdAddress();
        this.zipCode = String.valueOf(address.getZipCode());
        this.country = address.getCountry();
        this.city = address.getCity();
        this.state = address.getState();
        this.district = address.getDistrict();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
    }

    /**
     * Converter um dto para o model
     * @return Address
     */
    public Address toModel() {
        return new Address(this.idAddress, Long.parseLong(this.zipCode), this.country, this.city, this.state, this.district,
                this.street, this.number, this.complement);
    }
}
