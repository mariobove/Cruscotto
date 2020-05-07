package it.cyberSec.cruscotto.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.cyberSec.cruscotto.model.StatoLogin;
import it.cyberSec.cruscotto.repository.StatoLoginRepository;


@Configuration
public class SetStatiLogin {


	@Autowired
	private StatoLoginRepository statoRepository;
	
	@Bean
	public void buildStatiLogin() {
		if(!statoRepository.existsByStato("REGISTRATO").booleanValue()) {
			StatoLogin statoRegistrato = new StatoLogin("REGISTRATO");
			statoRepository.save(statoRegistrato);
		}
		if(!statoRepository.existsByStato("APPROVATO").booleanValue()) {
			StatoLogin statoApprovato = new StatoLogin("APPROVATO");
			statoRepository.save(statoApprovato);
		}
		
		if(!statoRepository.existsByStato("SOSPESO").booleanValue()) {
			StatoLogin statoSospeso = new StatoLogin("SOSPESO");
			statoRepository.save(statoSospeso);
		}
	}
}
