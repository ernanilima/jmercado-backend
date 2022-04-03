package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;
import br.com.ernanilima.jmercadobackend.repository.CompanyRepository;
import br.com.ernanilima.jmercadobackend.security.UserSpringSecurity;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.UserService;
import br.com.ernanilima.jmercadobackend.service.exception.DataIntegrityException;
import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import br.com.ernanilima.jmercadobackend.service.logging.LogToEntity;
import br.com.ernanilima.jmercadobackend.support.SupportUser;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private SupportUser supportUser;

    /**
     * Inserir uma empresa
     * @param companyDto CompanyDto
     * @return Company
     */
    @Override
    public Company insert(CompanyDto companyDto) {
        Company company = companyDto.toModel();
        LogToEntity.toInsert(company);
        LogToEntity.toInsert(company.getAddress());
        LogToEntity.toInsert(company.getTelephone());
        // insere uma empresa e retorna seus dados
        Company companyDatabase = insertUpdate(company);
        // insere um usuario
        insertUser(companyDatabase);
        return companyDatabase;
    }

    /**
     * Inserir o usuario principal da empresa
     * @param company Company
     */
    private void insertUser(Company company) {
        User user = company.getUserList().get(0);
        user.setCompany(company);
        user.setPermissions(Arrays.stream(Permissions.values())
                // o usuario recebe todas as permissoes que nao sejam de suporte
                .map(p -> Permissions.toEnum((p.getId() == Permissions.SUPPORT.getId()) ? Permissions.USER.getId() : p.getId()))
                .collect(Collectors.toList()));
        userService.insert(user);
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
        // validar dados para registar log
        validationForUpdate(companyDatabase, company);
        return insertUpdate(company);
    }

    /**
     * Realiza a atualizacao dos dados para log na tabela
     * @param oldCompany Company
     * @param newCompany Company
     */
    private void validationForUpdate(Company oldCompany, Company newCompany) {
        if (oldCompany != null && oldCompany.equals(newCompany)) {
            // vai atualizar outra tabela
            LogToEntity.dontUpdate(oldCompany, newCompany);
        } else {
            // vai atualizar algum dado da empresa
            LogToEntity.toUpdate(oldCompany, newCompany);
        }

        if (oldCompany != null && oldCompany.getAddress() != null && newCompany != null && newCompany.getAddress() != null) {
            // ja tem emdereco vinculado
            newCompany.getAddress().setIdAddress(oldCompany.getAddress().getIdAddress());
            if (oldCompany.getAddress().equals(newCompany.getAddress()))
                // vai atualizar outra tabela
                LogToEntity.dontUpdate(oldCompany.getAddress(), newCompany.getAddress());
            else
                // vai atualizar algum dado do endereco
                LogToEntity.toUpdate(oldCompany.getAddress(), newCompany.getAddress());
        } else if (newCompany != null && newCompany.getAddress() != null) {
            // ainda nao tem endereco mas foi recebido um para ser vinculado
            LogToEntity.toInsert(newCompany.getAddress());
        }

        if (oldCompany != null && oldCompany.getTelephone() != null && newCompany != null && newCompany.getTelephone() != null) {
            // ja tem telefone vinculado
            newCompany.getTelephone().setIdTelephone(oldCompany.getTelephone().getIdTelephone());
            if (oldCompany.getTelephone().equals(newCompany.getTelephone()))
                // vai atualizar outra tabela
                LogToEntity.dontUpdate(oldCompany.getTelephone(), newCompany.getTelephone());
            else
                // vai atualizar algum dado do telefone
                LogToEntity.toUpdate(oldCompany.getTelephone(), newCompany.getTelephone());
        } else if (newCompany != null && newCompany.getTelephone() != null) {
            // ainda nao tem telefone mas foi recebido um para ser vinculado
            LogToEntity.toInsert(newCompany.getTelephone());
        }
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
     * Buscar uma empresa pelo id
     * @param idCompany UUID
     * @return Company
     */
    @Override
    public Company findById(UUID idCompany) {
        UserSpringSecurity loggedUser = UserSpringSecurity.getAuthenticatedUser();
        Optional<Company> model = loggedUser.getUsername().equals(supportUser.getEmail()) ?
                // se for um usuario de suporte
                companyRepository.findById(idCompany) :
                // se for um usuario normal
                companyRepository.findByIdCompanyAndEin(idCompany, loggedUser.getCompanyEin());
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

    /**
     * Buscar todas as empresas
     * @return List<Company>
     */
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Deletar uma empresa
     * @param idCompany UUID
     */
    @Override
    public void delete(UUID idCompany) {
        try {
            // busca antes de deletar para realizar as validacoes de existencia
            findById(idCompany);
            companyRepository.deleteById(idCompany);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    MessageFormat.format(I18n.getMessage(I18n.INTEGRITY_DELETE), I18n.getClassName(Company.class.getSimpleName()))
            );
        }
    }
}
