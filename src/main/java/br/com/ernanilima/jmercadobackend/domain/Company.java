package br.com.ernanilima.jmercadobackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(length = 36, unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String companyName; // razao social

    @Column(length = 50, nullable = false)
    private String tradingName; // nome fantasia

    @Column(length = 20, unique = true, nullable = false)
    private long ein; // cnpj

    @OneToOne(cascade = CascadeType.ALL)
    private Telephone telephone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<User> userList = new ArrayList<>();

    public Company(UUID id, String companyName, String tradingName, long ein) {
        this.id = id;
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.ein = ein;
    }
}
