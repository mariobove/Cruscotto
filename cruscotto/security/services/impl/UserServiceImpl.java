package it.cyberSec.cruscotto.security.services.impl;

import java.io.IOException;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.cyberSec.cruscotto.dto.DescrizioneDocumento;
import it.cyberSec.cruscotto.dto.LogOut;
import it.cyberSec.cruscotto.dto.LoginForm;
import it.cyberSec.cruscotto.dto.LoginUser;
import it.cyberSec.cruscotto.dto.RequestConfigurazione;
import it.cyberSec.cruscotto.dto.RequestRegistrazione;
import it.cyberSec.cruscotto.dto.ServerResponse;
import it.cyberSec.cruscotto.message.response.ListaPresenze;
import it.cyberSec.cruscotto.message.response.LogOutResponse;
import it.cyberSec.cruscotto.message.response.UserDto;
import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.Document;
import it.cyberSec.cruscotto.model.Role;
import it.cyberSec.cruscotto.model.RoleName;
import it.cyberSec.cruscotto.model.StatoLogin;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.ContrattiRepository;
import it.cyberSec.cruscotto.repository.DocumentRepository;
import it.cyberSec.cruscotto.repository.RoleRepository;
import it.cyberSec.cruscotto.repository.StatoLoginRepository;
import it.cyberSec.cruscotto.repository.UserRepository;
import it.cyberSec.cruscotto.security.services.AppService;
import it.cyberSec.cruscotto.security.services.UserService;
import javassist.NotFoundException;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContrattiRepository presenzeRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	StatoLoginRepository statoLoginRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AppService appService;
	
	@Autowired
	DocumentRepository documentRepository;
	

	
	/*
	@Override
	public LogOutResponse salvaLogout(LogOut logout) {
		
	
		Presenze pre = null;
		LogOutResponse resp = new LogOutResponse();
		
		User user = userRepository.findByUsername(logout.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username : " + logout.getUsername()));
		
		if(user!=null) {
			
			
			Set<Presenze> presenze;
			
			presenze=user.getPresenze();
			
			List<Presenze> sortedList = presenze.stream().collect(Collectors.toList());
		   
		
			
			// Sorting
			
			if (sortedList!= null && (sortedList.size()!=0 || !sortedList.isEmpty())) {

				Collections.sort(sortedList, new Comparator<Presenze>() {

				
					public int compare(Presenze events2, Presenze events1)
					{

						
							return events1.getIngresso().compareTo(events2.getIngresso());
		

					} }); }
			
			
			
    		DateFormat  sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    		DateFormat  sdflog = new SimpleDateFormat("dd-MM-yyyy");
    		
		    Date currentdate = new Date();
		    
		    String d= sdf.format(currentdate);
		    
		    String dataLogOut= sdflog.format(currentdate);
		    
			
			
		    for (Presenze it :sortedList ) {
		    	
		    	pre = new Presenze();
		    	pre=it;
		    	
		    	String dataLogIn= sdflog.format(pre.getIngresso());
		    	
		    	if(pre.getUscita()==null && dataLogIn.equalsIgnoreCase(dataLogOut)) {

				    
			        try {
			        	currentdate=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(d);
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
			        
					pre.setUscita(currentdate);
					
		    		
		    	}
		    	
		    	pre=presenzeRepository.saveAndFlush(pre);
		    	break;

		    }
			
		
			
			
		    
			
		}
		if(pre!=null) {
			
			resp.setUsername(logout.getUsername());
			resp.setLogin(pre.getIngresso());
			resp.setLogout(pre.getUscita());
			
			return resp;
		}
		return null;
	}


	@Override
	public Date salvaLogin(String username, String password) {
		
		Presenze p=null;
		Presenze pre = null;
		
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username : " + username));
		
		
		if(user!=null) {
			DateFormat  sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		    Date currentdate = new Date();
		    
		    String d= sdf.format(currentdate);
		    
	        try {
	        	currentdate=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			pre = new Presenze(currentdate,null);
		    //p=presenzeRepository.saveAndFlush(pre);
			
		}
		if(pre!=null) {
			
			Set<Presenze> presenze = new HashSet<>();
			presenze= user.getPresenze();
			presenze.add(pre);
			user.setPresenze(presenze);
			userRepository.saveAndFlush(user);
			
			return pre.getIngresso();
		}
		return null;
		
		
	}


	@Override
	public List<ListaPresenze> listaLoginUser(String username, String password) {
		
		List<ListaPresenze> listapre = new ArrayList<ListaPresenze>();
		
		Set<Presenze> presenze = new HashSet<>();
		
		User user=userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username : " + username));
		
		if(password.equalsIgnoreCase( user.getPassword())) {
			
			presenze=user.getPresenze();
			
             List<Presenze> sortedList = presenze.stream().collect(Collectors.toList());
		   
		
			
			// Sorting
			
			if (sortedList!= null && (sortedList.size()!=0 || !sortedList.isEmpty())) {

				Collections.sort(sortedList, new Comparator<Presenze>() {

				
					public int compare(Presenze events2, Presenze events1)
					{

						
							return events1.getIngresso().compareTo(events2.getIngresso());
		

					} }); }

			
			 for (Presenze it: sortedList ) {
				     ListaPresenze resp= new ListaPresenze();
			    	Presenze pre = it;
			    	resp.setLogin(pre.getIngresso()!=null ?pre.getIngresso():null);
			    	resp.setLogout(pre.getUscita()!=null ? pre.getUscita():null  );
			    	listapre.add(resp);
			    	
			 }
			 
			 return listapre;
		}

		
		return listapre;
	}




*/

	
	
	
	
	@SuppressWarnings({ "unlikely-arg-type", "unchecked" })
	@Override
	public List<UserDto> listaLoginSuperUser(String email) throws NotFoundException {

		List<UserDto> listauser = new ArrayList<UserDto>();
        Boolean isSuperUser = Boolean.FALSE;
		Set<Role> r = new HashSet<>();
		User user=userRepository.findByEmail(email).orElseThrow(
				() -> new NotFoundException("User Not Found with -> email : " + email));

		if(user!=null) {
           
			r=user.getRoles();
			List<Role> ruoli = r.stream().collect(Collectors.toList());
			
			for(Role ru:ruoli) {
				
				
				if("ROLE_ADMIN".equalsIgnoreCase(ru.getName().name())) {
					isSuperUser=Boolean.TRUE;
				}
										
			}

			if(isSuperUser) {

				List<User> users = userRepository.findAll();



				for(User u:users) {
					
					
					r=u.getRoles();
					List<Role> ruoliuser = r.stream().collect(Collectors.toList());

					UserDto usedto = new UserDto(u.getId(),
												u.getName(), 
												u.getCognome(), 
												u.getEmail(),
												ruoliuser.get(0).getName().name(),
												u.getStatoLogin().getStato(),
												u.getContrattoRecente());
					




                 
                     listauser.add(usedto);
				}


			}


		}


		return listauser;

	}


	@SuppressWarnings("unused")
	@Override
	public ServerResponse registraUtente(RequestRegistrazione req) throws NotFoundException, RuntimeException {

		ServerResponse resp = new ServerResponse();

		User user = userRepository.findById(req.getIdUtenteDaregistrare()).orElseThrow(
				() -> new NotFoundException("User Not Found with -> id : " + req.getIdUtenteDaregistrare() ));

		if(user!=null && req.getOperazione()==1) {

			Long idreg=(long)2;

			StatoLogin statoLogin=statoLoginRepository.getOne(idreg);


			Set<Role> roles = new HashSet<>();


			System.out.println(req.getRuoloselezionato());
			switch (req.getRuoloselezionato()) {
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "ROLE_HR":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_HR)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}



			user.setRoles(roles);

			user.setStatoLogin(statoLogin);
			


			System.out.println(statoLogin.getStato().toString());
			resp.setRegistrazioneResponse( statoLogin.getStato().toString());

/*            
			String mail=null;
			try {
				mail=appService.sendmail(user.getEmail(),user.getRoles().iterator().next().getName().toString());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			userRepository.saveAndFlush(user);
			return resp;
		}

		if(user!=null && req.getOperazione()==2) {

			Long idreg=(long)1;

			StatoLogin statoLogin=statoLoginRepository.getOne(idreg);

			Set<Role> roles = new HashSet<>();


			System.out.println(req.getRuoloselezionato());
			switch (req.getRuoloselezionato()) {
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "ROLE_HR":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_HR)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}



			user.setRoles(roles);

			user.setStatoLogin(statoLogin);
			
			System.out.println(statoLogin.getStato().toString());
			resp.setRegistrazioneResponse( statoLogin.getStato().toString());

			userRepository.saveAndFlush(user);
			return resp;
		}

		if(user!=null && req.getOperazione()==3) {

			StatoLogin statoLogin = null;
			user.setStatoLogin(statoLogin);
			userRepository.delete(user);
			resp.setRegistrazioneResponse("ELIMINATO");

			return resp;
		}

		return null;
	}


	@Override
	public ServerResponse configuraUtente(RequestConfigurazione req) throws NotFoundException, RuntimeException  {

		
		ServerResponse resp = new ServerResponse();
		
		User user = userRepository.findById(req.getId()).orElseThrow(
				() -> new NotFoundException("User Not Found with -> idUser : " + req.getId() ));
		if(user != null) {
			StatoLogin stato = statoLoginRepository.findByStato(req.getStato());
			
			Set<Role> roles = new HashSet<>();

			switch (req.getRuolo()) {
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "ROLE_HR":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_HR)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
			
			user.setRoles(roles);
			user.setStatoLogin(stato);
		
			
			if(user.getContratti().isEmpty()) {
				saveUser(user, req);
				resp.setRegistrazioneResponse("NUOVO");
			} else {
				if( user.getContrattoRecente().getFine() != null) {
					if(user.getContrattoRecente().getFine().before(new Date()))
					{
						saveUser(user, req);
						resp.setRegistrazioneResponse("CONFIGURATO");
					}else {
						resp.setRegistrazioneResponse("ATTIVO");
					}

				} else {
					resp.setRegistrazioneResponse("INDETERMINATO");
				}
			} 

		}else {
			resp.setRegistrazioneResponse("INESISTENTE");
		}
		return resp;
	}

	
	private void saveUser(User user, RequestConfigurazione req) {
		Date now = new Date();
		Contratto c = new Contratto();
		c.setInizio(now);
		
		if(req.getIsDeterminato())
		{
			Calendar data = Calendar.getInstance();
			data.setTime(now);
			data.add(Calendar.MONTH, req.getMesi());	
			c.setFine(data.getTime());
		}
		
		user.getContratti().add(c);
		user.setContratti(user.getContratti());
		userRepository.saveAndFlush(user);
	}
	
	@Override
	public List<DescrizioneDocumento> listaDocumentiUser(LoginUser loginRequest) throws NotFoundException{
		List<DescrizioneDocumento> listaDocumenti = new ArrayList<DescrizioneDocumento>();
		User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
				() -> new NotFoundException("User Not Found with -> email : " + loginRequest.getEmail()));
		if(user != null) {
			if(!user.getDocumenti().isEmpty()) {
				Set<Document> documenti =  user.getDocumenti();
				Set<DescrizioneDocumento> ListDescr = new HashSet<DescrizioneDocumento>();
				for(Document doc: documenti) {
					DescrizioneDocumento descr = new DescrizioneDocumento(doc.getId(),doc.getCategoria().getValore(),doc.getDescrizione(),doc.getFile().getFileName(), doc.getDataCaricamento());
					listaDocumenti.add(descr);			
				}
			} else {
				System.out.println("Documenti inesistenti!");	
			}
		}
		return listaDocumenti;
	}
	
	@Override
	public List<Contratto> listaContrattiUser(Long id) throws NotFoundException{
		List<Contratto> listaContratti = new ArrayList<Contratto>();
		User user=userRepository.findById(id).orElseThrow(
				() -> new NotFoundException("User Not Found with -> id : " + id));
		if(user != null) {
			if(!user.getContratti().isEmpty()) {
				Set<Contratto> contratti =  user.getContratti();
				for(Contratto contr: contratti) {
					listaContratti.add(contr);			
				}
			} else {
				System.out.println("Contratti inesistenti!");	
			}
		}
		return listaContratti;
		
	}
	
	
	
}
