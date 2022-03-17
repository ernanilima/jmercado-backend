package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.Telephone;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TelephoneDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID idTelephone;

    @Length(min = 10, max = 15, message = "{length.field}")
    private String telephone;

    @Length(min = 10, max = 15, message = "{length.field}")
    private String cellPhone;

    private boolean whatsappCellPhone;

    public TelephoneDto(Telephone telephone) {
        this.idTelephone = telephone.getIdTelephone();
        this.telephone = telephone.getTelephone();
        this.cellPhone = telephone.getCellPhone();
        this.whatsappCellPhone = telephone.isWhatsappCellPhone();
    }

    /**
     * Converter um dto para o model
     * @return Telephone
     */
    public Telephone toModel() {
        return new Telephone(this.idTelephone, this.telephone, this.cellPhone, this.whatsappCellPhone);
    }
}
