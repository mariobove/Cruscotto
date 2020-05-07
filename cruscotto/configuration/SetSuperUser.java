package it.cyberSec.cruscotto.configuration;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.cyberSec.cruscotto.model.Role;
import it.cyberSec.cruscotto.model.RoleName;
import it.cyberSec.cruscotto.model.StatoLogin;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.RoleRepository;
import it.cyberSec.cruscotto.repository.StatoLoginRepository;
import it.cyberSec.cruscotto.repository.UserRepository;

@Configuration
public class SetSuperUser {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private StatoLoginRepository statoRepository;

	
	private User superUser = new User();

	@Bean
	public void buildSuperUser() {
		if (!userRepository.existsByEmail("root@cybsec.it")) {
			superUser.setEmail("root@cybsec.it");
			superUser.setName("root");
			superUser.setPassword(encoder.encode("root123"));
			superUser.setCognome("root");
			Set<Role> roles = new HashSet<>();

			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not find."));
			roles.add(adminRole);
			superUser.setRoles(roles);
			
			StatoLogin stato = statoRepository.findByStato("APPROVATO");
			superUser.setStatoLogin(stato);
			userRepository.save(superUser);
		}

	}

}
