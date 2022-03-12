package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.dto.UserDto;
import br.com.ernanilima.jmercadobackend.repository.UserRepository;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private CompanyService companyService;

    /**
     * Inserir um usuario
     * @param userDto UserDto
     * @return User
     */
    @Override
    public User insert(UserDto userDto) {
        User user = userDto.toModel();
        // busca a empresa que vai ser atribuida ao usuario
        // a validacao eh realizada no metodo de busca da empresa
        user.setCompany(companyService.findById(user.getCompany().getIdCompany()));
        return insertUpdate(user);
    }

    /**
     * Atualizar um usuario
     * @param userDto UserDto
     * @return User
     */
    @Override
    public User update(UserDto userDto) {
        // busca o usuario que vai ser atualizado
        User userDatabase = findById(userDto.getIdUser());
        User user = userDto.toModel();
        // dados para nao atualizar
        user.setCompany(userDatabase.getCompany());
        return insertUpdate(user);
    }

    /**
     * Inserir ou atualizar um usuario
     * @param user User
     * @return User
     */
    private User insertUpdate(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    MessageFormat.format(I18n.getMessage(I18n.INTEGRITY_IN_UP), I18n.getFieldName(e))
            );
        }
    }

    /**
     * Busca um usuario pelo id
     * @param idUser UUID
     * @return User
     */
    @Override
    public User findById(UUID idUser) {
        Optional<User> model = userRepository.findById(idUser);
        return model.orElseThrow(() ->
                // cria uma mensagem de erro
                new ObjectNotFoundException(
                        MessageFormat.format(
                                I18n.getMessage(I18n.NOT_FOUND_USER), I18n.getClassName(User.class.getSimpleName())
                        )
                )
        );
    }

    /**
     * Buscar um usuario pelo email
     * @param email String
     * @return User
     */
    @Override
    public User findByEmail(String email) {
        Optional<User> model = userRepository.findByEmail(email);
        return model.orElseThrow(() ->
                new ObjectNotFoundException(
                        MessageFormat.format(
                                I18n.getMessage(I18n.NOT_FOUND_EMAIL), email, I18n.getClassName(User.class.getSimpleName())
                        )
                )
        );
    }

    /**
     * Buscar todos os usuarios
     * @return List<User>
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Deletar um usuario
     * @param idUser UUID
     */
    @Override
    public void delete(UUID idUser) {
        // busca antes de deletar para realizar as validacoes de existencia
        findById(idUser);
        userRepository.deleteById(idUser);
    }
}
