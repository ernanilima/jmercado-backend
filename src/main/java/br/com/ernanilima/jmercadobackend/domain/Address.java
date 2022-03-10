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
@Table(name = "address")
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID id;

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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Address(UUID id, long zipCode, String country, String city, String state, String district, String street, String number, String complement) {
        this.id = id;
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
