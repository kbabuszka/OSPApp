package net.babuszka.osp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserPasswordForm;
import net.babuszka.osp.service.UserService;

@Controller
public class UserProfileController {

	@Value("${user.passwordchange.message.success}")
	private String messagePasswordChanged;
	
	@Value("${user.passwordchange.message.error}")
	private String messagePasswordNotChanged;
	
	@Value("${user.profileupdate.message.success}")
	private String messageProfileUpdated;
	
	@Value("${user.profileupdate.message.error}")
	private String messageProfileNotUpdated;
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path = "/profile")
	public String displayProfile(Model model) {
		model.addAttribute("page_title", "Twój profil");
		User user = userService.getCurrentlyLoggedUser();
		Firefighter firefighter = user.getFirefighter();
		model.addAttribute("user", user);
		model.addAttribute("firefighter", firefighter);
		model.addAttribute("passwordForm", new UserPasswordForm());
		return "user_profile";
	}
	
	@PostMapping(path = "/profile")
	public String updateProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("page_title", "Twój profil");
		if(bindingResult.hasErrors()) {
			int totalErrors = bindingResult.getErrorCount();
			int passwordErrors = bindingResult.getFieldErrorCount("password");
			if(totalErrors != passwordErrors) {
				model.addAttribute("message", messageProfileNotUpdated);
			    model.addAttribute("alertClass", "alert-danger");
				model.addAttribute("user", user);
				model.addAttribute("passwordForm", new UserPasswordForm());
				return "user_profile";
			} else {
				// if there is only password error, update the user
				redirectAttributes.addFlashAttribute("message", messageProfileUpdated);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
				userService.updateUser(user);
				return "redirect:/profile";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", messageProfileUpdated);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			userService.updateUser(user);
			return "redirect:/profile";
		}

	}
	
	@PostMapping(path = "/profile/change-password")
	public String changePassword(@ModelAttribute("passwordForm") @Valid UserPasswordForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("page_title", "Twój profil");
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
