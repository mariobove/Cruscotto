package it.cyberSec.cruscotto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.ConfGen;


@Repository
public interface ConfGenRepository extends JpaRepository<ConfGen, Long>{
	Boolean existsByChiave(String chiave);
	
	Boolean existsByChiaveAndValore(String chiave, String valore);

	Optional<ConfGen> findByChiaveAndValore(String chiave, String valore);

	Optional<List<ConfGen>> findByChiave(String chiave);
}
