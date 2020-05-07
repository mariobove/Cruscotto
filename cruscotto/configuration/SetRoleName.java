package it.cyberSec.cruscotto.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.cyberSec.cruscotto.model.Role;
import it.cyberSec.cruscotto.model.RoleName;
import it.cyberSec.cruscotto.repository.RoleRepository;

@Configuration
public class SetRoleName {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Bean
	public void buildRoleName() {
		if(!roleRepository.existsByName(RoleName.ROLE_USER)) {
			Role roleUser = new Role(RoleName.ROLE_USER);
			roleRepository.save(roleUser);
		}
		
		if(!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
			Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
			roleRepository.save(roleAdmin);
			}
		
		if(!roleRepository.existsByName(RoleName.ROLE_HR)) {
			Role roleHR = new Role(RoleName.ROLE_HR);
			roleRepository.save(roleHR);
		}


		
	}

}
