package br.com.ernanilima.jmercadobackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telephone")
public class Telephone implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID idTelephone;

    @Column(length = 15)
    private String telephone;

    @Column(length = 15)
    private String cellPhone;

    private boolean whatsappCellPhone;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Telephone(UUID idTelephone, String telephone, String cellPhone, boolean whatsappCellPhone) {
        this.idTelephone = idTelephone;
        this.telephone = telephone;
        this.cellPhone = cellPhone;
        this.whatsappCellPhone = whatsappCellPhone;
    }
}
