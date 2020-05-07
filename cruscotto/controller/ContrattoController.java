package it.cyberSec.cruscotto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.ContrattiRepository;
import it.cyberSec.cruscotto.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contratti")
public class ContrattoController {
	
	@Autowired
	ContrattiRepository contrattiRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/create/{idUser}")
    public String createContratto(@RequestBody Contratto contratto, @PathVariable Long idUser) {
		if(userRepository.existsById(idUser)) {
			User user = userRepository.findById(idUser).get();
			if(controlloAggiungiContratto(contratto, user.getContrattoRecente())) {
				Contratto newContratto = new Contratto(contratto.getInizio(), contratto.getFine(), contratto.getOreSettimanali(), new Date(), user);
		        contrattiRepository.save(newContratto);
		        return "OK";
			} else {
				return "FALLITO";
			}
		} else {
			return "UTENTE_NON_TROVATO";
		}
    }
	
	@PutMapping("/update/{idUser}")
	public String updateContratto(@RequestBody Contratto contratto, @PathVariable Long idUser) {
		if(userRepository.existsById(idUser)) {
			User user = userRepository.findById(idUser).orElseThrow();
			//contrattiRepository.deleteById(user.getContrattoRecente().getId());
			Contratto contrattoRecente = user.getContrattoRecente();
			Set<Contratto> vecchiContratti = new HashSet<Contratto>();
			for(Contratto c: user.getContratti()) {
				if(!c.equals(contrattoRecente)) {
					vecchiContratti.add(c);
				}
			}
			if(controlloAggiungiContratto(contratto, getContrattoRecente(vecchiContratti))) {
				contratto.setUser(user);
				contrattiRepository.save(contratto);
		        return "OK";
			} else {
				return "FALLITO";
			}		
		} else {
			return "UTENTE_NON_TROVATO";
		}
	}
	
	private Boolean controlloAggiungiContratto(Contratto contratto, Contratto contrattoRecente) {
		if(contrattoRecente == null) {
			return true;
		}
		//Contratto contrattoRecente = user.getContrattoRecente();
		if(contrattoRecente.getFine()==null) {
			if(contratto.getInizio().after(contrattoRecente.getInizio())) {
				return true;
			} else {
				System.out.println(contratto.getInizio());
				System.out.println(contrattoRecente.getInizio());
				System.out.println("1");
				return false;
			}
		}else if(contratto.getInizio().after(contrattoRecente.getFine())){
			return true;
		} else {
			System.out.println(contratto.getInizio());
			System.out.println(contrattoRecente.getFine());
			System.out.println("2");
			return false;
		}
		
	}
	
	/*
	@PutMapping("/delete/{idContratto}")
	public ResponseEntity<String> deleteContratto(@PathVariable Long idContratto, @RequestBody Long idUser) {
		User user = userRepository.findById(idUser).get();
		Contratto contratto = contrattiRepository.findById(idContratto).get();
		user.rimuoviContratto(contratto);
		userRepository.save(user);
		contrattiRepository.deleteById(idContratto);
		return ResponseEntity.ok("ELIMINATO");
		
	}
	*/
	public Contratto getContrattoRecente(Set<Contratto> contratti) {
		if(!contratti.isEmpty()) {
			Contratto recente = contratti.iterator().next();
				for(Contratto c : contratti)
				{
					if(c.getInizio().after(recente.getInizio()))
						recente = c;
				}
			return recente;
		}else {
			return null;
		}
			
	}

}
