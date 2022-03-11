package br.com.ernanilima.jmercadobackend.repository;

import br.com.ernanilima.jmercadobackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);
}
