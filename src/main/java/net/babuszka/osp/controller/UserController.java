package net.babuszka.osp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserPasswordForm;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.UserService;

@Controller
public class UserController {

	@Value("${user.passwordchange.message.success}")
	private String messagePasswordChanged;
	
	@Value("${user.passwordchange.message.error}")
	private String messagePasswordNotChanged;
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayProfile(Model model) {
		User user = userService.getCurrentlyLoggedUser();
		Firefighter firefighter = user.getFirefighter();
		model.addAttribute("user", user);
		model.addAttribute("firefighter", firefighter);
		model.addAttribute("passwordForm", new UserPasswordForm());
		return "user_profile";
	}
	
	@RequestMapping(path = "/profile", method = RequestMethod.POST)
	public String updateProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			int totalErrors = bindingResult.getErrorCount();
			int passwordErrors = bindingResult.getFieldErrorCount("password");
			if(totalErrors != passwordErrors) {
				model.addAttribute("user", user);
				model.addAttribute("passwordForm", new UserPasswordForm());
				return "user_profile";
			} else {
				// if there is only password error, update the user
				userService.updateUser(user);
				return "redirect:/profile";
			}
		} else {
			userService.updateUser(user);
			return "redirect:/profile";
		}

	}
	
	@RequestMapping(path = "/profile/change-password", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("passwordForm") @Valid UserPasswordForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		User user = userService.getCurrentlyLoggedUser();
		Firefighter firefighter = user.getFirefighter();
		String oldPassword = form.getOldPassword();
		String newPassword1 = form.getNewPassword1();
		String newPassword2 = form.getNewPassword2();
			
		if(userService.checkIfPasswordMatch(user, oldPassword)) {
			if(newPassword1.equals(newPassword2) ) {
				user.setPassword(newPassword1);
				userService.updateUserPassword(user);
				redirectAttributes.addFlashAttribute("message", messagePasswordChanged);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
				return "redirect:/profile";
			} else {
				model.addAttribute("message", messagePasswordNotChanged);
			    model.addAttribute("alertClass", "alert-danger");
				bindingResult.rejectValue("newPassword2", "user.passwordchange.password2.nomatch");
			}
		} else {
			model.addAttribute("message", messagePasswordNotChanged);
			model.addAttribute("alertClass", "alert-danger");
			bindingResult.rejectValue("oldPassword", "user.passwordchange.oldpassword.nomatch");
		}
		
		model.addAttribute("user", user);
		model.addAttribute("firefighter", firefighter);
		model.addAttribute("passwordForm", form);
		return "user_profile";
	}
	
}
