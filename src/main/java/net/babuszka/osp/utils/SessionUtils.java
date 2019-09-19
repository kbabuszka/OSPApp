package net.babuszka.osp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.User;
import net.babuszka.osp.service.UserService;

@ControllerAdvice
public class SessionUtils {
	
	public SessionUtils() {
		super();
	}
	
	@Autowired
	private UserService userService;

	@ModelAttribute("global_session_username")
	public String getLoggedUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null)
			return authentication.getName();
		else {
			return "<brak użytkownika>";
		}
	}
	
	@ModelAttribute("global_session_name")
	public String getLoggedName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String username = authentication.getName();
			
			if(!username.equals("anonymousUser")) {
				User user = userService.findUserByUsername(username);
				if (user != null) {
					Firefighter firefighter = user.getFirefighter();
					if(firefighter != null) {
						String name = firefighter.getFirstName() + " " + firefighter.getLastName();
						return name;
					} else {
						return user.getDisplayName();
					}
				}
			}
		} 
		return "Użytkownik";
	}
	
	@ModelAttribute("global_session_email")
	public String getLoggedEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String username = authentication.getName();
			
			if(!username.equals("anonymousUser")) {
				User user = userService.findUserByUsername(username);
				if (user != null) {
					Firefighter firefighter = user.getFirefighter();
					if(firefighter != null) {
						return firefighter.getEmail();
					} else {
						return user.getEmail();
					}
				}
			}
		}
		return "";
	}
	
	
}
