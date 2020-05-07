package it.cyberSec.cruscotto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.ConfGen;

@Repository
public interface AppRepository extends JpaRepository<ConfGen, Long>{

}
