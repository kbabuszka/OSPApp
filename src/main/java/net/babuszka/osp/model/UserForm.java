package net.babuszka.osp.model;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class UserForm {

	private Integer id;
	private String username;
	private String displayName;
	private String email;
	private String password;
	private String confirmPassword;
	private Firefighter firefighter;
	private List<UserRole> userRoles;
	
	public UserForm() {
		super();
	}
	
	public UserForm(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.displayName = user.getDisplayName();
		this.email = user.getEmail();
		this.firefighter = user.getFirefighter();
		this.userRoles = user.getRoles();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public Firefighter getFirefighter() {
		return firefighter;
	}
	
	public void setFirefighter(Firefighter firefighter) {
		this.firefighter = firefighter;
	}
	
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(List<UserRole> roles) {
		this.userRoles = roles;
	}

}
