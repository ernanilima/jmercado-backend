package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company insert(CompanyDto companyDto) {
        Company company = companyDto.toModel();
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        return null;
    }

    /**
     * Buscar todas as empresas
     * @return List<Company>
     */
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Buscar empresa pelo cnpj
     * @param ein String
     * @return Company
     */
    @Override
    public Company findByEin(String ein) {
        Optional<Company> model = companyRepository.findByEin(ein);
        return model.orElseThrow(() ->
                // cria uma mensagem de erro
                new ObjectNotFoundException(
                        MessageFormat.format(
                                I18n.getSimpleMessage(I18n.NOT_FOUND_EIN), ein, I18n.getClassSimpleNameI18n(Company.class.getSimpleName())
                        )
                )
        );
    }

    @Override
    public void delete(Company company) {

    }
}
