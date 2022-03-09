package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company insert(Company company) {
        return null;
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

    @Override
    public void delete(Company company) {

    }
}
