package br.com.ernanilima.jmercadobackend.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company implements IEntityLog, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID idCompany;

    @Column(length = 50, nullable = false)
    private String companyName; // razao social

    @Column(length = 50, nullable = false)
    private String tradingName; // nome fantasia

    @Column(length = 20, unique = true, nullable = false)
    private String ein; // cnpj

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private Telephone telephone;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private Address address;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company")
    private List<User> userList = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private Timestamp dateInsert;
    @EqualsAndHashCode.Exclude
    private Timestamp dateUpdate;
    @EqualsAndHashCode.Exclude
    private Timestamp dateDelete;

    public Company(UUID idCompany, String companyName, String tradingName, String ein, String email) {
        this.idCompany = idCompany;
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.ein = ein;
        this.email = email;
    }
}
