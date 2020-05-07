package it.cyberSec.cruscotto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.ModificheContratto;

@Repository
public interface ModificheContrattoRepository  extends JpaRepository<ModificheContratto, Long>{
	public Optional<List<ModificheContratto>> findByContratto(Contratto contratto);
}
