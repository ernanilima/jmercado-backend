package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.domain.Company;

import java.util.List;

public interface CompanyService {
    Company insert(Company company);
    Company update(Company company);
    List<Company> findAll();
    void delete(Company company);
}
