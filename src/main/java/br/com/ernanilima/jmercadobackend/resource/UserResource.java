package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.dto.UserChangePasswordDto;
import br.com.ernanilima.jmercadobackend.dto.UserDto;
import br.com.ernanilima.jmercadobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * Inserir um usuario
     * @param userDto UserDto
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody UserDto userDto) {
        userService.insert(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(userDto.getIdUser()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Atualizar uma usuario
     * @param userDto UserDto
     * @param idUser UUID
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value = "/{idUser}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody UserDto userDto, @PathVariable UUID idUser) {
        userDto.setIdUser(idUser);
        userService.update(userDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar um usuario pelo id
     * @param idUser UUID
     * @return ResponseEntity<User>
     */
    @RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable UUID idUser) {
        User user = userService.findById(idUser);
        return ResponseEntity.ok().body(user);
    }

    /**
     * Buscar um usuario pelo email
     * @param email String
     * @return ResponseEntity<User>
     */
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok().body(user);
    }

    /**
     * Buscar todos os usuarios
     * @return ResponseEntity<List<UserDto>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> userList = userService.findAll();
        List<UserDto> userDtoList = userList
                .stream().map(UserDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(userDtoList);
    }

    /**
     * Deletar um usuario
     * @param idUser UUID
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable UUID idUser) {
        userService.delete(idUser);
        return ResponseEntity.noContent().build();
    }

    /**
     * Alterar a senha de um usuario
     * @param userChangePasswordDto UserChangePasswordDto
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value = "/change-password", method = RequestMethod.PUT)
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UserChangePasswordDto userChangePasswordDto) {
        userService.changePassword(userChangePasswordDto);
        return ResponseEntity.noContent().build();
    }
}
