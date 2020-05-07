package it.cyberSec.cruscotto.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.cyberSec.cruscotto.dto.DescrizioneDocumento;
import it.cyberSec.cruscotto.dto.LoginUser;

import it.cyberSec.cruscotto.dto.RequestConfigurazione;
import it.cyberSec.cruscotto.dto.RequestRegistrazione;
import it.cyberSec.cruscotto.dto.ServerResponse;
import it.cyberSec.cruscotto.message.response.ListaPresenze;
import it.cyberSec.cruscotto.message.response.UserDto;
import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.Document;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.DocumentRepository;
import it.cyberSec.cruscotto.repository.RoleRepository;
import it.cyberSec.cruscotto.repository.UserRepository;
import it.cyberSec.cruscotto.security.jwt.JwtProvider;
import it.cyberSec.cruscotto.security.services.AppService;
import it.cyberSec.cruscotto.security.services.UserService;
import javassist.NotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	AppService appService;
	
	@Autowired
	UserService userService;

	@Autowired
	DocumentRepository documentRepository;
	
	@PostMapping("/listaUtentiSuperUser")
	public ResponseEntity<?> listaPresenzeSuperUser(@RequestBody LoginUser loginRequest) throws NotFoundException, RuntimeException {

	
		List<UserDto> listauser;
		
		listauser=userService.listaLoginSuperUser(loginRequest.getEmail());
		
		if(listauser==null || listauser.isEmpty()) {
			
			UserDto listavuota= new UserDto(null, null, null, null, null,null, null);
			
			listauser.add(listavuota);
			
		}
		
		return ResponseEntity.ok(listauser);
	}
	
	@PostMapping("/listaDocumenti")
	public ResponseEntity<?> listaDocumentiUser(@RequestBody LoginUser loginRequest) throws NotFoundException {
	
		List<DescrizioneDocumento> listaDocumenti;
		
		listaDocumenti = userService.listaDocumentiUser(loginRequest);
	
		if(listaDocumenti==null || listaDocumenti.isEmpty()) {
			
			DescrizioneDocumento listavuota= new DescrizioneDocumento(null,null, null, null, null);
			
			listaDocumenti.add(listavuota);
			
		}
		return ResponseEntity.ok(listaDocumenti);	
	}
	
	@PostMapping("/listaContratti")
	public ResponseEntity<?> listaContrattiUser(@RequestBody Long id) throws NotFoundException {
	
		List<Contratto> listaContratti;
		
		listaContratti = userService.listaContrattiUser(id);
	
		if(listaContratti==null || listaContratti.isEmpty()) {
			
			Contratto listavuota= new Contratto(null, null, null, null, null);
			
			listaContratti.add(listavuota);
			
		}
		return ResponseEntity.ok(listaContratti);	
	}
	
	
	
	@PostMapping("/abilitaUtente")
	public ResponseEntity<?> abilitaUser(@RequestBody RequestRegistrazione req) throws NotFoundException, RuntimeException {

        ServerResponse serverResponse = null; 	
        
        serverResponse=userService.registraUtente(req);
        
        
		
		return ResponseEntity.ok(serverResponse);
	}
	
	@PutMapping("/configuraUtente")
	public ResponseEntity<?> configuraUser(@RequestBody RequestConfigurazione req) throws NotFoundException, RuntimeException{
		ServerResponse serverResponse = null;
		
		serverResponse = userService.configuraUtente(req);
		
		return ResponseEntity.ok(serverResponse);
		
	}
	
	
	/*
	@GetMapping(value = "/reportListaPresenze",produces = "text/csv; charset=utf-8")
	@ResponseStatus(HttpStatus.OK)
	public void reportListaPresenze(@RequestBody LoginUser req,HttpServletRequest request,HttpServletResponse response)throws Exception {

        	
        
		appService.exportPresenzeUtente(req.getIdUser(), request, response);
        
   
	}
	
	*/
	

}