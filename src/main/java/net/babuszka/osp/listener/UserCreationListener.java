package net.babuszka.osp.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import net.babuszka.osp.event.OnUserCreationEvent;
import net.babuszka.osp.model.User;
import net.babuszka.osp.service.MailService;
import net.babuszka.osp.service.UserService;

@Component
public class UserCreationListener implements ApplicationListener<OnUserCreationEvent> {
	
	@Value("${email.newaccount.created.title}")
	private String userAddedEmailTitle;
	
	@Value("${app.config.url}")
	private String globalApplicationUrl;

	private UserService userService;
	private MailService mailService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setEmailUtils(MailService emailUtils) {
		this.mailService = emailUtils;
	}
	
	@Override
	public void onApplicationEvent(OnUserCreationEvent event) {
		this.confirmUserEmail(event);	
	}
	
	private void confirmUserEmail(OnUserCreationEvent event) {
		User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createUserVerificationToken(user, token);  
		try {
			Context context = new Context();
			context.setVariable("displayName", user.getDisplayName());
			context.setVariable("username", user.getUsername());
			context.setVariable("global_application_url", globalApplicationUrl);
			context.setVariable("token", token);
			mailService.sendHtmlMessage(user.getEmail(), userAddedEmailTitle, "accountCreated", context);
		} catch (Exception e) {
			e.printStackTrace();
		}        

	}

	
}
