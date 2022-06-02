package br.com.ernanilima.jmercadobackend.domain.addressBr;

import br.com.ernanilima.jmercadobackend.domain.IEntityLog;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addressbr")
public class AddressBR implements IEntityLog, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(length = 36, unique = true)
    private UUID idAddressBR;

    @Column(length = 8, unique = true, nullable = false)
    private String cep; // zipCode

    @Column(length = 50, nullable = false)
    private String pais = "Brasil"; // country

    @JsonProperty("localidade")
    @Column(length = 50, nullable = false)
    private String cidade; // city

    @Column(length = 50, nullable = false)
    private String uf; //state

    @Column(length = 50, nullable = false)
    private String bairro; //district

    @Column(length = 50, nullable = false)
    private String logradouro; //street

    @Transient
    private Boolean erro;

    @EqualsAndHashCode.Exclude
    private Timestamp dateInsert;
    @EqualsAndHashCode.Exclude
    private Timestamp dateUpdate;
    @EqualsAndHashCode.Exclude
    private Timestamp dateDelete;

    public AddressBR(UUID idAddressBR, String cep, String pais, String cidade, String uf, String bairro, String logradouro) {
        this.idAddressBR = idAddressBR;
        this.cep = cep;
        this.pais = pais;
        this.cidade = cidade;
        this.uf = uf;
        this.bairro = bairro;
        this.logradouro = logradouro;
    }
}
