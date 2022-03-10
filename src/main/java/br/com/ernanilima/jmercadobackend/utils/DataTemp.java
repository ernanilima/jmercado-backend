package br.com.ernanilima.jmercadobackend.utils;

import br.com.ernanilima.jmercadobackend.domain.Address;
import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.Telephone;
import br.com.ernanilima.jmercadobackend.repository.AddressRepository;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class DataTemp {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Construir dados para o banco de dados
     */
    public void createDataDatabase() {
        Address address1 = new Address(null, 80060110L, "Brasil", "Curitiba", "Paraná", "Centro", "Rua Tibagi", "S/N", "Apto 104");
        Telephone telephone1 = new Telephone(null, "4133333333", "41999999999", false);
        Company company1 = new Company(null, "Desktop Informatica ltda", "Desktop informatica", "00000000000191");
        telephone1.setCompany(company1);
        address1.setCompany(company1);
        company1.setTelephone(telephone1);
        company1.setAddress(address1);

        Address address2 = new Address(null, 68746040L, "Brasil", "Castanhal", "Pará", "Jaderlandia", "Rua Francisco Pereira Lago", "2", "Q 6");
        Company company2 = new Company(null, "Ernani ltda", "Ernani informatica", "00000000000272");
        address2.setCompany(company2);
        company2.setAddress(address2);

//        addressRepository.saveAll(Arrays.asList(address1, address2));
        companyRepository.saveAll(Arrays.asList(company1, company2));
    }
}
