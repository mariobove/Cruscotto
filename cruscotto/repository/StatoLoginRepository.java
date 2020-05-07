package it.cyberSec.cruscotto.repository;






import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.StatoLogin;


@Repository
public interface StatoLoginRepository extends JpaRepository<StatoLogin, Long> {

	Boolean existsByStato(String stato);
	
	StatoLogin findByStato(String stato);

}