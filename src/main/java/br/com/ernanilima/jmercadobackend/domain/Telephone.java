package br.com.ernanilima.jmercadobackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@ToString
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
    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @EqualsAndHashCode.Exclude
    private Timestamp dateInsert;
    @EqualsAndHashCode.Exclude
    private Timestamp dateUpdate;
    @EqualsAndHashCode.Exclude
    private Timestamp dateDelete;

    public Telephone(UUID idTelephone, String telephone, String cellPhone, boolean whatsappCellPhone) {
        this.idTelephone = idTelephone;
        this.telephone = telephone;
        this.cellPhone = cellPhone;
        this.whatsappCellPhone = whatsappCellPhone;
    }
}
