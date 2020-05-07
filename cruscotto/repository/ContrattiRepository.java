package it.cyberSec.cruscotto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.Contratto;

@Repository
public interface ContrattiRepository  extends JpaRepository<Contratto, Long>{

}
