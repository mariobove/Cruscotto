package it.cyberSec.cruscotto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.DBFile;
import it.cyberSec.cruscotto.model.Document;
import it.cyberSec.cruscotto.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findById(Long id);
    Boolean existsByEmail(String email);
}