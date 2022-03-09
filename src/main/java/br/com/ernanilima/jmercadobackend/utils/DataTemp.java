package br.com.ernanilima.jmercadobackend.utils;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataTemp {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Construir dados para o banco de dados
     */
    public void createDataDatabase() {
        Company company = new Company(UUID.randomUUID(), "Desktop Informatica ltda", "Desktop informatica", "00000000000191");

        companyRepository.save(company);
    }
}
