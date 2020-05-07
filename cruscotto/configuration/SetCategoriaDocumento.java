package it.cyberSec.cruscotto.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.cyberSec.cruscotto.model.ConfGen;
import it.cyberSec.cruscotto.repository.ConfGenRepository;

@Configuration
public class SetCategoriaDocumento {

	@Autowired
	ConfGenRepository confGenRepo;

	@Bean
	public void buildCatDoc() {
		if (!confGenRepo.existsByChiaveAndValore("cat_doc", "Documento Fiscale")) {
			ConfGen confGen = new ConfGen("cat_doc", "Documento Fiscale", false);
			confGenRepo.save(confGen);
		}
		if (!confGenRepo.existsByChiaveAndValore("cat_doc", "Busta Paga")) {
			ConfGen confGen = new ConfGen("cat_doc", "Busta Paga", false);
			confGenRepo.save(confGen);
		}
		if (!confGenRepo.existsByChiaveAndValore("cat_doc", "Contratto")) {
			ConfGen confGen = new ConfGen("cat_doc", "Contratto", false);
			confGenRepo.save(confGen);
		}
		if (!confGenRepo.existsByChiaveAndValore("cat_doc", "Documenti Informativo")) {
			ConfGen confGen = new ConfGen("cat_doc", "Documenti Informativo", false);
			confGenRepo.save(confGen);
		}
	}
}