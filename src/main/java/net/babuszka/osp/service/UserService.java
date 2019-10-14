package net.babuszka.osp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.User;
import net.babuszka.osp.repository.RoleRepository;
import net.babuszka.osp.repository.UserRepository;
import net.babuszka.osp.utils.SessionUtils;

@Service
public class UserService {

	private Logger LOG = LoggerFactory.getLogger(Logger.class);
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("Brak użytkownika: " + username);
		return user;
	}
	
	public User findUserByFirefighterId(Integer id) {
		User user = userRepository.findByFirefighterId(id);
		if(user == null)
			LOG.debug("Brak użytkownika przypisanego do strażaka o ID: " + id);
		return user;
	}
	
	public User getCurrentlyLoggedUser() {
		SessionUtils sessionUtils = new SessionUtils();
		String username = sessionUtils.getLoggedUsername();
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("Brak użytkownika");
		return user;
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User userToUpdate) {
		User foundUser = userRepository.findByUsername(userToUpdate.getUsername());
		try {
			foundUser.setDisplayName(userToUpdate.getDisplayName());
			foundUser.setEmail(userToUpdate.getEmail());
			userRepository.save(foundUser);
		} catch (Exception e) {
			LOG.error("An error occured during update of user: " + e.getMessage());
		}
		
	}
	
	public Boolean checkIfPasswordMatch(User user, String password) {		
		return (passwordEncoder.matches(password, user.getPassword())) ? true : false;
	}

	public void updateUserPassword(User user) {
		String plainPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(plainPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

}
