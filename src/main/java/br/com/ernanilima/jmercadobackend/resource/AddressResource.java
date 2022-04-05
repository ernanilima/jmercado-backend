package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.addressBr.Country;
import br.com.ernanilima.jmercadobackend.domain.addressBr.CountryRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class AddressResource {

    /**
     * ComboBox com os paises
     * @return ResponseEntity<List<Country.ComboBox>>
     */
    @RequestMapping(value = "/pais", method = RequestMethod.GET)
    public ResponseEntity<List<Country.ComboBox>> findAllCountry() {
        return ResponseEntity.ok().body(Country.getComboBox());
    }

    /**
     * ComboBox com as regioes do pais
     * @return ResponseEntity<List<Country.ComboBox>>
     */
    @RequestMapping(value = "/regiao/{codeCountry}", method = RequestMethod.GET)
    public ResponseEntity<List<CountryRegion.ComboBox>> findAllCountryRegion(@PathVariable String codeCountry) {
        return ResponseEntity.ok().body(CountryRegion.getComboBox(codeCountry));
    }
}
