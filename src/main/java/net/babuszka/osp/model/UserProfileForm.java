package net.babuszka.osp.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserProfileForm {

	private Integer id;
	private String username;
	private String displayName;
	private String email;
	private Firefighter firefighter;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	
	public UserProfileForm() {
		super();
	}
	
	public UserProfileForm(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.displayName = user.getDisplayName();
		this.email = user.getEmail();
		this.firefighter = user.getFirefighter();
	}

}
