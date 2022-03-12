package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User insert(UserDto userDto);
    User update(UserDto userDto);
    User findById(UUID idUser);
    User findByEmail(String email);
    List<User> findAll();
    void delete(UUID idUser);
}
