package br.com.ernanilima.jmercadobackend.repository;

import br.com.ernanilima.jmercadobackend.domain.addressBr.AddressBR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressBRRepository extends JpaRepository<AddressBR, UUID> {
    @Transactional(readOnly = true)
    Optional<AddressBR> findByCep(String zipCode);
}
