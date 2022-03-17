package br.com.ernanilima.jmercadobackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "telephone")
public class Telephone implements IEntityLog, Serializable {
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

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @EqualsAndHashCode.Exclude
    @NotNull
    private Timestamp dateInsert;
    @EqualsAndHashCode.Exclude
    @NotNull
    private Timestamp dateUpdate;

    public Telephone(UUID idTelephone, String telephone, String cellPhone, boolean whatsappCellPhone) {
        this.idTelephone = idTelephone;
        this.telephone = telephone;
        this.cellPhone = cellPhone;
        this.whatsappCellPhone = whatsappCellPhone;
    }
}
