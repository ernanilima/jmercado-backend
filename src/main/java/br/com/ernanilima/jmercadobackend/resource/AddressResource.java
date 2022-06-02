package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.addressBr.AddressBR;
import br.com.ernanilima.jmercadobackend.domain.addressBr.Country;
import br.com.ernanilima.jmercadobackend.domain.addressBr.CountryRegion;
import br.com.ernanilima.jmercadobackend.domain.addressBr.State;
import br.com.ernanilima.jmercadobackend.dto.AddressDto;
import br.com.ernanilima.jmercadobackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class AddressResource {

    @Autowired private AddressService addressService;

    /**
     * Busca o endereco com base no cep
     * @param zipcode String
     * @return ResponseEntity<AddressDto>
     */
    @RequestMapping(value = "/cep/{zipcode}", method = RequestMethod.GET)
    public ResponseEntity<AddressDto> findByZipCode(@PathVariable String zipcode) {
        AddressBR addressBR = addressService.findByZipCode(zipcode);
        return ResponseEntity.ok().body(new AddressDto(addressBR));
    }

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
     * @param codeCountry String
     * @return ResponseEntity<List<Country.ComboBox>>
     */
    @RequestMapping(value = "/regiao/{codeCountry}", method = RequestMethod.GET)
    public ResponseEntity<List<CountryRegion.ComboBox>> findAllCountryRegion(@PathVariable String codeCountry) {
        return ResponseEntity.ok().body(CountryRegion.getComboBox(codeCountry));
    }

    /**
     * ComboBox com os estados/uf do pais.
     * Endpoint pode receber apenas um dos parametros, essa validacao eh feita ao criar o combobox.
     * @param codeCountry String
     * @param countryRegion String
     * @return ResponseEntity<List<State.ComboBox>>
     */
    @RequestMapping(value = "/estado", method = RequestMethod.GET)
    public ResponseEntity<List<State.ComboBox>> findAllState(
            @RequestParam(value = "pais", required = false) String codeCountry,
            @RequestParam(value = "regiao", required = false) String countryRegion
    ) {
        return ResponseEntity.ok().body(State.getComboBox(codeCountry, countryRegion));
    }
}
