package br.com.ernanilima.jmercadobackend.repository;

import br.com.ernanilima.jmercadobackend.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
