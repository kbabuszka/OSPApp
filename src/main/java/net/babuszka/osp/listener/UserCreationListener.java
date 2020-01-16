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
import net.babuszka.osp.service.SettingsService;
import net.babuszka.osp.service.UserService;

@Component
public class UserCreationListener implements ApplicationListener<OnUserCreationEvent> {
	
	@Value("${email.newaccount.created.title}")
	private String userAddedEmailTitle;

	private UserService userService;
	private MailService mailService;
	private SettingsService settingsService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setEmailUtils(MailService emailUtils) {
		this.mailService = emailUtils;
	}
	
	@Autowired
	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
	public UserCreationListener() {
		super();
	}

	@Override
	public void onApplicationEvent(OnUserCreationEvent event) {
		this.confirmUserEmail(event);	
	}
	
	private void confirmUserEmail(OnUserCreationEvent event) {
		User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createUserVerificationToken(user, token);  
        String globalApplicationUrl = settingsService.getByName("DEPARTMENT_APP_URL").getValue();
        String departmentFullName = settingsService.getByName("DEPARTMENT_FULL_NAME").getValue();
        String departmentAddressStreet = settingsService.getByName("DEPARTMENT_ADDRESS_STREET").getValue();
        String departmentAddressCity = settingsService.getByName("DEPARTMENT_ADDRESS_CITY").getValue();
        String departmentAddressPostalCode = settingsService.getByName("DEPARTMENT_ADDRESS_POSTAL_CODE").getValue();
		try {
			Context context = new Context();
			context.setVariable("displayName", user.getDisplayName());
			context.setVariable("username", user.getUsername());
			context.setVariable("token", token);
			context.setVariable("global_application_url", globalApplicationUrl);
			context.setVariable("department_full_name", departmentFullName);
			context.setVariable("department_address_street", departmentAddressStreet);
			context.setVariable("department_address_city", departmentAddressCity);
			context.setVariable("department_address_postal_code", departmentAddressPostalCode);
			mailService.sendHtmlMessage(user.getEmail(), userAddedEmailTitle, "accountCreated", context);
		} catch (Exception e) {
			e.printStackTrace();
		}        
	}
}
