package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.addressBr.Country;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class AddressResource {

    @RequestMapping(value = "/pais", method = RequestMethod.GET)
    public ResponseEntity<List<Country.ComboBox>> findAllCountry() {
        return ResponseEntity.ok().body(Country.getComboBox());
    }
}
