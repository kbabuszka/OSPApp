package net.babuszka.osp.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.babuszka.osp.model.Role;
import net.babuszka.osp.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private RoleRepository roleRepository;
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}



	@Override
	public void run(String... args) throws Exception {
		
		Role user = new Role();
		user.setId(1);
		user.setName("User");
		user.setDescription("Uprawnienia do logowania w aplikacji");
		roleRepository.save(user);
		
		Role mobileUser = new Role();
		mobileUser.setId(2);
		mobileUser.setName("Mobile User");
		mobileUser.setDescription("Uprawnienia do logowania w aplikacji mobilnej");
		roleRepository.save(mobileUser);
		
		Role operator = new Role();
		operator.setId(3);
		operator.setName("Operator");
		operator.setDescription("Dostęp do obsługi zdarzeń");
		roleRepository.save(operator);
		
		Role administrator = new Role();
		administrator.setId(4);
		administrator.setName("Administrator");
		administrator.setDescription("Pełny dostęp do każdego modułu w aplikacji");
		roleRepository.save(administrator);
		
	}
}
