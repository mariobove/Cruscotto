package it.cyberSec.cruscotto.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.cyberSec.cruscotto.dto.LogOut;
import it.cyberSec.cruscotto.dto.LoginForm;
import it.cyberSec.cruscotto.dto.SignUpForm;
import it.cyberSec.cruscotto.message.response.JwtResponse;
import it.cyberSec.cruscotto.message.response.ResponseMessage;
import it.cyberSec.cruscotto.model.Role;
import it.cyberSec.cruscotto.model.RoleName;
import it.cyberSec.cruscotto.model.StatoLogin;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.RoleRepository;
import it.cyberSec.cruscotto.repository.StatoLoginRepository;
import it.cyberSec.cruscotto.repository.UserRepository;
import it.cyberSec.cruscotto.security.jwt.JwtProvider;
import it.cyberSec.cruscotto.security.services.UserPrinciple;
import it.cyberSec.cruscotto.security.services.UserService;
import it.cyberSec.cruscotto.utils.UserException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginCyberAcademy {

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
	UserService userService;
	
	@Autowired
	StatoLoginRepository statoLoginRepository;

	//private Date salvaLogin;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest){
		
		
		

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		UserPrinciple up=(UserPrinciple) authentication.getPrincipal();
		
		
		if(!up.getStatoLogin().equalsIgnoreCase("APPROVATO")) {
		     throw new UserException("UTENTE NON APPROVATO");
		}
		
		
		
		
		
		

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));
	}
	
	
	
	
	@PostMapping("/logout")
	public ResponseEntity<?> logOutUser(@RequestBody LogOut logoutRequest) {

		//return ResponseEntity.ok(userService.salvaLogout(logoutRequest));
		return new ResponseEntity<>(new ResponseMessage("User login successfully!"), HttpStatus.OK);

	}

	
	
	
	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already taken!"),
					HttpStatus.BAD_REQUEST);
		}



		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getCognome(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));


		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(userRole);
		
		user.setRoles(roles);
		Long idreg=(long)1;
		StatoLogin statoLogin=statoLoginRepository.getOne(idreg);
		user.setStatoLogin(statoLogin);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}