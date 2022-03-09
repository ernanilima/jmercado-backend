package br.com.ernanilima.jmercadobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, unique = true, nullable = false, updatable = false)
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

}
