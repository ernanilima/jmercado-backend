package br.com.ernanilima.jmercadobackend.service.impl;

import br.com.ernanilima.jmercadobackend.domain.addressBr.AddressBR;
import br.com.ernanilima.jmercadobackend.repository.AddressBRRepository;
import br.com.ernanilima.jmercadobackend.service.AddressService;
import br.com.ernanilima.jmercadobackend.service.exception.DataIntegrityException;
import br.com.ernanilima.jmercadobackend.service.logging.LogToEntity;
import br.com.ernanilima.jmercadobackend.utils.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressBRRepository addressBRRepository;
    @Autowired
    private WebClient webClient;

    @Override
    public AddressBR findByZipCode(String zipCode) {
        // buscar no banco de dados
        return addressBRRepository.findByCep(zipCode)
                // buscar na API do ViaCep
                .orElseGet(() -> findByZipCodeApi(zipCode));
    }

    private AddressBR findByZipCodeApi(String zipCode) {
        Mono<AddressBR> mono = webClient.method(HttpMethod.GET).uri("{zipCode}/json", zipCode).retrieve().bodyToMono(AddressBR.class);
        AddressBR addressBR = mono.block();
        if (addressBR != null && addressBR.getErro() == null) {
            // endereco encontrado na API do ViaCep
            addressBR.setCep(zipCode);
            LogToEntity.toInsert(addressBR);
            addressBRRepository.save(addressBR);
            return addressBR;
        } else {
            throw new DataIntegrityException(MessageFormat.format(I18n.getMessage(I18n.NOT_FOUND_ZIP_CODE), zipCode));
        }
    }
}
