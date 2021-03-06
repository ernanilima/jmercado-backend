package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    Company insert(CompanyDto companyDto);
    Company update(CompanyDto companyDto);
    Company findById(UUID idCompany);
    Company findByEin(String ein);
    List<Company> findAll();
    void delete(UUID idCompany);
}
