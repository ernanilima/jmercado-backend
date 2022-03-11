package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    Company insert(CompanyDto companyDto);
    Company update(Company company);
    List<Company> findAll();
    Company findByEin(String ein);
    void delete(Company company);
}
