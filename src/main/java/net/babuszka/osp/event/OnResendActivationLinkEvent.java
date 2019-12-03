package net.babuszka.osp.event;

import org.springframework.context.ApplicationEvent;

import net.babuszka.osp.model.User;

public class OnResendActivationLinkEvent extends ApplicationEvent {

	private User user;

	public OnResendActivationLinkEvent(User user) {
		super(user);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
