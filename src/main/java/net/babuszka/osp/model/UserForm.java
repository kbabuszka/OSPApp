package net.babuszka.osp.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserForm {

	private class RoleCheckbox {
		
		public RoleCheckbox(Role role, Boolean checked) {
			this.role = role;
			this.checked = checked;
		}
		
		private Role role;
		private Boolean checked;
		
		@SuppressWarnings("unused")
		public Role getRole() {
			return role;
		}
		@SuppressWarnings("unused")
		public void setRole(Role role) {
			this.role = role;
		}
		@SuppressWarnings("unused")
		public Boolean getChecked() {
			return checked;
		}
		@SuppressWarnings("unused")
		public void setChecked(Boolean checked) {
			this.checked = checked;
		}
		
	}
	
	private Integer id;
	private String username;
	private String displayName;
	private String email;
	private String password;
	private String confirmPassword;
	private Firefighter firefighter;
	private List<UserRole> userRoles;
	private List<Role> roles;
	private List<RoleCheckbox> roleCheckboxes;
	
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
		this.userRoles = user.getUserRoles();
	}
	
	public UserForm(User user, List<Role> roles) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.displayName = user.getDisplayName();
		this.email = user.getEmail();
		this.firefighter = user.getFirefighter();
		this.userRoles = user.getUserRoles();
		
		this.roleCheckboxes = roles.stream()
		    .map(role -> new RoleCheckbox(role, user.getUserRoles().stream()
		    		.anyMatch(userRole -> userRole.getRole().equals(role))))
		    .collect(Collectors.toList());
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<RoleCheckbox> getRoleCheckboxes() {
		return roleCheckboxes;
	}

	public void setRoleCheckboxes(List<RoleCheckbox> roleCheckboxes) {
		this.roleCheckboxes = roleCheckboxes;
	}

}
