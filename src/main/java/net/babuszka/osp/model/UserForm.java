package net.babuszka.osp.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserForm {

	@Getter
	@Setter
	private class RoleCheckbox {
		
		public RoleCheckbox(Role role, Boolean checked) {
			this.role = role;
			this.checked = checked;
		}		
		private Role role;
		private Boolean checked;	
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

	public List<RoleCheckbox> getRoleCheckboxes() {
		return roleCheckboxes;
	}

	public void setRoleCheckboxes(List<RoleCheckbox> roleCheckboxes) {
		this.roleCheckboxes = roleCheckboxes;
	}

}
