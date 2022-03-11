package br.com.ernanilima.jmercadobackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String companyName; // razao social

    @Column(length = 50, nullable = false)
    private String tradingName; // nome fantasia

    @Column(length = 20, unique = true, nullable = false)
    private String ein; // cnpj

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private Telephone telephone;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<User> userList = new ArrayList<>();

    public Company(UUID id, String companyName, String tradingName, String ein, String email) {
        this.id = id;
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.ein = ein;
        this.email = email;
    }
}
