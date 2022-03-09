package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class CompanyResource {

    @Autowired
    private CompanyService companyService;

    /**
     * Buscar todas as empresas
     * @return ResponseEntity<List<Company>>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Company>> findAll() {
        List<Company> companyList = companyService.findAll();
        return ResponseEntity.ok().body(companyList);
    }

    /**
     * Buscar empresa pelo cnpj
     * @param cnpj String
     * @return ResponseEntity<Company>
     */
    @RequestMapping(value = "/{cnpj}", method = RequestMethod.GET)
    public ResponseEntity<Company> findByEin(@PathVariable String cnpj) {
        Company company = companyService.findByEin(cnpj);
        return ResponseEntity.ok().body(company);
    }
}
