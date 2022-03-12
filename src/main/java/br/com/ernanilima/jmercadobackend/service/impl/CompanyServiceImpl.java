package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.exception.DataIntegrityException;
import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Inserir uma empresa
     * @param companyDto CompanyDto
     * @return Company
     */
    @Override
    public Company insert(CompanyDto companyDto) {
        Company company = companyDto.toModel();
        return insertUpdate(company);
    }

    /**
     * Atualizar uma empresa
     * @param companyDto CompanyDto
     * @return Company
     */
    @Override
    public Company update(CompanyDto companyDto) {
        // busca a empresa que vai ser atualizada
        Company companyDatabase = findById(companyDto.getIdCompany());
        Company company = companyDto.toModel();
        // atribui o id do endereco que esta vinculado
        company.getAddress().setIdAddress(companyDatabase.getAddress().getIdAddress());
        return insertUpdate(company);
    }

    /**
     * Inserir ou atualizar uma empresa
     * @param company Company
     * @return Company
     */
    private Company insertUpdate(Company company) {
        try {
            return companyRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            // cria uma mensagem de erro
            throw new DataIntegrityException(
                    MessageFormat.format(I18n.getMessage(I18n.INTEGRITY_IN_UP), I18n.getFieldName(e))
            );
        }
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
     * Buscar uma empresa pelo id
     * @param idCompany UUID
     * @return Company
     */
    @Override
    public Company findById(UUID idCompany) {
        Optional<Company> model = companyRepository.findById(idCompany);
        return model.orElseThrow(() ->
                // cria uma mensagem de erro
                new ObjectNotFoundException(
                        MessageFormat.format(
                                I18n.getMessage(I18n.NOT_FOUND_COMPANY), I18n.getClassName(Company.class.getSimpleName())
                        )
                )
        );
    }

    /**
     * Buscar uma empresa pelo cnpj
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
                                I18n.getMessage(I18n.NOT_FOUND_EIN), ein, I18n.getClassName(Company.class.getSimpleName())
                        )
                )
        );
    }

    @Override
    public void delete(UUID idCompany) {

    }
}
