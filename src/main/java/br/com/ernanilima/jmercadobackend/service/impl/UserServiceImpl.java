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

    @Override
    public User insert(UserDto userDto) {
        User user = userDto.toModel();
        return insertUpdate(user);
    }

    @Override
    public User update(UserDto userDto) {
        User user = userDto.toModel();
        return insertUpdate(user);
    }

    /**
     * Inserir ou atualizar um usuario
     * @param user User
     * @return User
     */
    private User insertUpdate(User user) {
        try {
            user.setCompany(companyService.findByEin(user.getCompany().getEin()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    MessageFormat.format(I18n.getMessage(I18n.INTEGRITY_IN_UP), I18n.getFieldName(e))
            );
        }
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

    @Override
    public void delete(UUID id) {

    }
}
