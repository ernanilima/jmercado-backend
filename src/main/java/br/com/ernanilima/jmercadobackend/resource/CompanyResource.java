package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empresa")
public class CompanyResource {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CompanyDto companyDto) {
        companyService.insert(companyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(companyDto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Buscar todas as empresas
     * @return ResponseEntity<List<Company>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CompanyDto>> findAll() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDto> companyDtoList = companyList
                .stream().map(CompanyDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(companyDtoList);
    }

    /**
     * Buscar empresa pelo cnpj
     * @param ein String
     * @return ResponseEntity<Company>
     */
    @RequestMapping(value = "/{ein}", method = RequestMethod.GET)
    public ResponseEntity<Company> findByEin(@PathVariable String ein) {
        Company company = companyService.findByEin(ein);
        return ResponseEntity.ok().body(company);
    }
}
