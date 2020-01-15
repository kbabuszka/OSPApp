package net.babuszka.osp.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserPasswordForm {

	@Autowired
	public UserPasswordForm() {
		super();
	}
	
	@NotEmpty
	private String oldPassword;
	
	@NotEmpty
	private String newPassword1;
	
	@NotEmpty
	private String newPassword2;

}
