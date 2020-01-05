package net.babuszka.osp.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.babuszka.osp.event.OnResendActivationLinkEvent;
import net.babuszka.osp.event.OnUserCreationEvent;
import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserRole;
import net.babuszka.osp.model.UserStatus;
import net.babuszka.osp.model.UserVerificationToken;
import net.babuszka.osp.repository.FirefighterRepository;
import net.babuszka.osp.repository.UserRepository;
import net.babuszka.osp.repository.UserRoleRepository;
import net.babuszka.osp.repository.UserVerificationTokenRepository;
import net.babuszka.osp.utils.SessionUtils;
import net.babuszka.osp.utils.UserUtils;

@Service
public class UserService {

	private Logger LOG = LoggerFactory.getLogger(Logger.class);
	private UserRepository userRepository;
	private UserVerificationTokenRepository tokenRepository;
	private UserRoleRepository userRoleRepository;
	private FirefighterRepository firefighterRepository;
	private PasswordEncoder passwordEncoder;
	private UserUtils userUtils;
	
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setTokenRepository(UserVerificationTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	@Autowired
	public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Autowired
	public void setFirefighterRepository(FirefighterRepository firefighterRepository) {
		this.firefighterRepository = firefighterRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Autowired
	public void setUserUtils(UserUtils userUtils) {
		this.userUtils = userUtils;
	}
	
	@Autowired
	public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(Integer id) {
		LOG.debug("Getting User with following ID: " + id);
		try {
			LOG.debug("Found User: " + userRepository.getOne(id).getUsername());
			return userRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting user's details: " + e.getMessage());
		}
		return null;
	}
	
	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null)
			LOG.debug("Brak użytkownika o loginie: " + username);
		return user;
	}
	
	public User findUserByFirefighterId(Integer id) {
		User user = userRepository.findByFirefighterId(id);
		if(user == null)
			LOG.debug("No user with following ID: " + id);
		return user;
	}
	
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null)
			LOG.debug("No user with following email: " + email);
		return user;
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public void addNewUser(User user) {
		userRepository.save(user);
		if(user.getId() != null) {
			try {
				eventPublisher.publishEvent(new OnUserCreationEvent(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Firefighter firefighter = user.getFirefighter();
			if(firefighter != null) {
				firefighter.setUser(user);
				firefighterRepository.save(firefighter);
			}
		}
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
	
	public User getCurrentlyLoggedUser() {
		SessionUtils sessionUtils = new SessionUtils();
		String username = sessionUtils.getLoggedUsername();
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("No user found");
		return user;
	}

	public void updateUserPassword(User user) {
		String plainPassword = user.getPassword();
		String encodedPassword = userUtils.encodePassword(plainPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	public void createUserVerificationToken(User user, String token) {
		UserVerificationToken newToken = new UserVerificationToken(user, token);
		tokenRepository.save(newToken);
	}
	
	public void saveUserRole(UserRole role) {
		userRoleRepository.save(role);
	}
	
	@Transactional
	public void deleteUserRoles(Integer userId) {
		userRoleRepository.deleteByUserId(userId);
	}
	
	public UserStatus verifyUserEmail(String token) {
		UserVerificationToken userToken = tokenRepository.findByToken(token);
		User userToVerify;
		LocalDateTime tokenExpiration;
		if(userToken !=null) {
			userToVerify = userToken.getUser();
			tokenExpiration = userToken.getExpirationDate();
			if((userToVerify !=null) && ( LocalDateTime.now().isBefore(tokenExpiration))) {
				userToVerify.setStatus(UserStatus.ACTIVE);
				userRepository.save(userToVerify);
				tokenRepository.deleteById(userToken.getId());
				return userToVerify.getStatus();
			}
		}
		return UserStatus.INACTIVE;
	}
	
	public void resendActivationLink(Integer id) {
		LOG.debug("Resending activation link for user: " + id);
		User user = userRepository.getOne(id);
		if(user != null && user.getStatus() == UserStatus.INACTIVE) {
			try {
				eventPublisher.publishEvent(new OnResendActivationLinkEvent(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean deactivateUser(Integer id) {
		LOG.debug("Deactivating User with following ID: " + id);
		User userToDeactivate = userRepository.getOne(id);
		if(userToDeactivate != null 
				&& userToDeactivate.getStatus() != UserStatus.INACTIVE) {
			userToDeactivate.setStatus(UserStatus.INACTIVE);
			userRepository.save(userToDeactivate);
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public void deleteUser(Integer id) {
		LOG.debug("Deleting User with following ID: " + id);
		User userToDelete = userRepository.getOne(id);		
		Firefighter firefighter = userToDelete.getFirefighter();
		if(firefighter != null) {
			firefighter.setUser(null);
			firefighterRepository.save(firefighter);
		}
		userToDelete.setUserRoles(null);
		userToDelete.setFirefighter(null);
		userRepository.save(userToDelete);
		deleteUserVerificationToken(id);
		userRoleRepository.deleteByUserId(id);
		userRepository.delete(userToDelete);	
		
	}
	
	@Transactional
	public void deleteUserVerificationToken(Integer userId) {
		tokenRepository.deleteByUserId(userId);
	}

	public Boolean checkIfPasswordMatch(User user, String password) {		
		return (passwordEncoder.matches(password, user.getPassword())) ? true : false;
	}
}
