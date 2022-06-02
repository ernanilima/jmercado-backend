package br.com.ernanilima.jmercadobackend.service;

import br.com.ernanilima.jmercadobackend.domain.addressBr.AddressBR;

public interface AddressService {
    AddressBR findByZipCode(String zipCode);
}
