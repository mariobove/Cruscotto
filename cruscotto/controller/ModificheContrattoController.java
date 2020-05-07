package it.cyberSec.cruscotto.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.ModificheContratto;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.ContrattiRepository;
import it.cyberSec.cruscotto.repository.ModificheContrattoRepository;
import it.cyberSec.cruscotto.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/modifichecontratti")
public class ModificheContrattoController {

	@Autowired
	ModificheContrattoRepository modificheContrattoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContrattiRepository contrattiRepository;
	
	@PostMapping("/create")
	public String createContratto(@RequestBody ModificheContratto contratto) {
		System.out.println(contratto.getUser());
		System.out.println(contratto.getContratto());
		ModificheContratto newContratto = new ModificheContratto(	contratto.getInizio(), 
																	contratto.getFine(), 
																	contratto.getOreSettimanali(), 
																	new Date(), 
																	contratto.getUser(), 
																	contratto.getContratto());
		modificheContrattoRepository.save(newContratto);
		return "OK";
    }
	
	@GetMapping("/get/{idContratto}")
	public List<ModificheContratto> getModificheContratto(@PathVariable Long idContratto) {
		Contratto contratto = contrattiRepository.findById(idContratto).get();
		if(contrattiRepository.existsById(idContratto)) {
			if(!contratto.getModificheContratto().isEmpty()) {
				return modificheContrattoRepository.findByContratto(contratto).get();
			} else {
				System.out.println("Non ci sono modifiche per questo contratto");
				return null;
			}
		} else {
			System.out.println("Il contratto non esiste!");
			return null;
		}
	}
	
	
}
