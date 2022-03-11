package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.User;
import br.com.ernanilima.jmercadobackend.dto.UserDto;
import br.com.ernanilima.jmercadobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
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
                .path("/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).build();
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
     * Buscar um usuario pelo email
     * @param email String
     * @return ResponseEntity<User>
     */
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok().body(user);
    }
}
