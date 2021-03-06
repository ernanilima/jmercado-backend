package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.dto.UserChangePasswordDto;
import br.com.ernanilima.jmercadobackend.dto.UserDto;
import br.com.ernanilima.jmercadobackend.repository.UserRepository;
import br.com.ernanilima.jmercadobackend.security.UserSpringSecurity;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.UserService;
import br.com.ernanilima.jmercadobackend.service.exception.ChangePasswordException;
import br.com.ernanilima.jmercadobackend.service.exception.DataIntegrityException;
import br.com.ernanilima.jmercadobackend.service.exception.ObjectNotFoundException;
import br.com.ernanilima.jmercadobackend.service.logging.LogToEntity;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Inserir um usuario
     * @param userDto UserDto
     * @return User
     */
    @Override
    public User insert(UserDto userDto) {
        User user = userDto.toModel();
        // busca a empresa que vai ser atribuida ao usuario
        // a validacao de existencia eh realizada no metodo de busca da empresa
        user.setCompany(companyService.findById(user.getCompany().getIdCompany()));
        return insert(user);
    }

    /**
     * Inserir um usuario, usado principalmente quando inserir uma empresa
     * @param user User
     * @return User
     */
    @Override
    public User insert(User user) {
        // codificar senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LogToEntity.toInsert(user);
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
        user.setPassword(userDatabase.getPassword());
        user.setCompany(userDatabase.getCompany());
        // validar dados para registar log
        validationForUpdate(userDatabase, user);
        return insertUpdate(user);
    }

    private void validationForUpdate(User oldUser, User newUser) {
        if (oldUser != null && oldUser.equals(newUser)) {
            // vai atualizar outra tabela
            LogToEntity.dontUpdate(oldUser, newUser);
        } else {
            // vai atualizar algum dado do usuario
            LogToEntity.toUpdate(oldUser, newUser);
        }
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
     * Buscar um usuario pelo seu email.
     * Realiza a busca na mesma empresa atribuida ao usuario solicitante
     * @param email String
     * @return User
     */
    @Override
    public User findByEmail(String email) {
        return findByEmail(email, UserSpringSecurity.getAuthenticatedUser().getCompanyEin());
    }

    /**
     * Buscar um usuario pelo seu email e o cnpj da empresa que ele pertence
     * @param email String
     * @param companyEin String
     * @return User
     */
    @Override
    public User findByEmail(String email, String companyEin) {
        Optional<User> model = userRepository.findByEmailAndCompany_Ein(email, companyEin);
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

    /**
     * Alterar a senha do usuario
     * @param userChangePasswordDto UserChangePasswordDto
     */
    @Override
    public void changePassword(UserChangePasswordDto userChangePasswordDto) {
        // senhas novas combinarem
        if (userChangePasswordDto.getNewPassword1().equals(userChangePasswordDto.getNewPassword2())) {
            // busca um usuario com base no e-mail que contem no token
            User userDatabase = findByEmail(UserSpringSecurity.getAuthenticatedUser().getUsername());
            // senha atual que foi recebida eh a mesma que consta no banco de dados
            // basicamente confirma que o usuario que esta solicitando a alteracao sabe a senha atual
            if (userDatabase != null && passwordEncoder.matches(userChangePasswordDto.getOldPassword(), userDatabase.getPassword())) {
                // muda a senha do usuario
                userDatabase.setPassword(passwordEncoder.encode(userChangePasswordDto.getNewPassword1()));
                // atualiza o log
                LogToEntity.toUpdate(userDatabase, userDatabase);
                insertUpdate(userDatabase);
            } else {
                throw new ChangePasswordException("Dados inv??lidos para a altera????o");
            }
        } else {
            throw new ChangePasswordException("Senhas n??o combinam");
        }
    }
}
