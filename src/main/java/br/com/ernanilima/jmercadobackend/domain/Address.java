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
@Table(name = "address")
public class Address implements IEntityLog, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID idAddress;

    @Column(length = 8, nullable = false)
    private long zipCode; // cep

    @Column(length = 50, nullable = false)
    private String country; // pais

    @Column(length = 50, nullable = false)
    private String city; // cidade

    @Column(length = 50, nullable = false)
    private String state; // estado

    @Column(length = 50, nullable = false)
    private String district; // bairro

    @Column(length = 50, nullable = false)
    private String street; // rua

    @Column(length = 10, nullable = false)
    private String number; // numero

    @Column(length = 50)
    private String complement; // complemento

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

    public Address(UUID idAddress, long zipCode, String country, String city, String state, String district, String street, String number, String complement) {
        this.idAddress = idAddress;
        this.zipCode = zipCode;
        this.country = country;
        this.city = city;
        this.state = state;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }
}
