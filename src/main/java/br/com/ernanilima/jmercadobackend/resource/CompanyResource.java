package br.com.ernanilima.jmercadobackend.resource;

import br.com.ernanilima.jmercadobackend.domain.Company;
import br.com.ernanilima.jmercadobackend.dto.CompanyDto;
import br.com.ernanilima.jmercadobackend.service.CompanyService;
import br.com.ernanilima.jmercadobackend.service.ReCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empresa")
public class CompanyResource {

    @Autowired
    private ReCaptchaService reCaptchaService;
    @Autowired
    private CompanyService companyService;

    /**
     * Inserir uma empresa
     * @param companyDto CompanyDto
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CompanyDto companyDto, HttpServletRequest request) {
        // validar recaptcha
        String response = request.getParameter("g-recaptcha-response");
        reCaptchaService.processResponse(response);

        Company company = companyService.insert(companyDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idCompany}").buildAndExpand(company.getIdCompany().toString()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Atualizar uma empresa
     * @param companyDto CompanyDto
     * @param idCompany UUID
     * @return ResponseEntity<Void>
     */
    @PreAuthorize("hasAnyRole('COMPANY_UPDATE')")
    @RequestMapping(value = "/{idCompany}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CompanyDto companyDto, @PathVariable UUID idCompany) {
        companyDto.setIdCompany(idCompany);
        companyService.update(companyDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar uma empresa pelo id
     * @param idCompany UUID
     * @return ResponseEntity<CompanyDto>
     */
    @PreAuthorize("hasAnyRole('COMPANY_FIND')")
    @RequestMapping(value = "/{idCompany}", method = RequestMethod.GET)
    public ResponseEntity<CompanyDto> findById(@PathVariable UUID idCompany) {
        Company company = companyService.findById(idCompany);
        return ResponseEntity.ok().body(new CompanyDto(company));
    }

    /**
     * Buscar uma empresa pelo cnpj
     * @param ein String
     * @return ResponseEntity<CompanyDto>
     */
    @PreAuthorize("hasAnyRole('COMPANY_FIND')")
    @RequestMapping(value = "/cnpj/{ein}", method = RequestMethod.GET)
    public ResponseEntity<CompanyDto> findByEin(@PathVariable String ein) {
        Company company = companyService.findByEin(ein);
        return ResponseEntity.ok().body(new CompanyDto(company));
    }

    /**
     * Buscar todas as empresas
     * @return ResponseEntity<List<CompanyDto>>
     */
    @PreAuthorize("hasAnyRole('SUPPORT')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CompanyDto>> findAll() {
        List<Company> companyList = companyService.findAll();
        List<CompanyDto> companyDtoList = companyList
                .stream().map(CompanyDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(companyDtoList);
    }

    /**
     * Deletar uma empresa
     * @param idCompany UUID
     * @return ResponseEntity<Void>
     */
    @PreAuthorize("hasAnyRole('SUPPORT')")
    @RequestMapping(value = "/{idCompany}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable UUID idCompany) {
        companyService.delete(idCompany);
        return ResponseEntity.noContent().build();
    }
}
