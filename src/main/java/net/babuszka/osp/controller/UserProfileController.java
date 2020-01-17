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

import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserProfileForm;
import net.babuszka.osp.service.UserService;
import net.babuszka.osp.utils.UserUtils;

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
	private UserUtils userUtils;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setUserUtils(UserUtils userUtils) {
		this.userUtils = userUtils;
	}

	@GetMapping(path = "/profile")
	public String initEditProfileForm(Model model) {
		User user = userService.getCurrentlyLoggedUser();
		model.addAttribute("page_title", "Twój profil");
		model.addAttribute("userProfileForm", new UserProfileForm(user));
		return "user_profile";
	}
	
	@PostMapping(path = "/profile")
	public String processEditProfileForm(@ModelAttribute("userProfileForm") @Valid UserProfileForm userProfileForm, 
			BindingResult bindingResult, 
			Model model, 
			RedirectAttributes redirectAttributes) {
		Integer userId = userProfileForm.getId();
		User user = userService.getUser(userId);
		String oldPassword = userProfileForm.getOldPassword();
		String newPassword = userProfileForm.getNewPassword();
		String confirmNewPassword = userProfileForm.getConfirmNewPassword();
		model.addAttribute("page_title", "Twój profil");
		
		if(userProfileForm.getEmail().isEmpty())
			bindingResult.rejectValue("email", "user.email.empty");
		
		if(userService.findUserByEmail(userProfileForm.getEmail()) != null
				&& userService.findUserByEmail(userProfileForm.getEmail()) != user)
			bindingResult.rejectValue("email", "user.email.duplicate");
		
		if(userService.findUserByUsername(userProfileForm.getUsername()) != null
				&& userService.findUserByUsername(userProfileForm.getUsername()) != user)
			bindingResult.rejectValue("username", "user.username.duplicate");
		
		if(!(oldPassword == null) && !(oldPassword.equals(""))
				&& !userService.checkIfPasswordMatch(user, oldPassword)) {
			bindingResult.rejectValue("oldPassword", "user.passwordchange.oldpassword.nomatch");
		}
		
		if(!(newPassword == null) && !(newPassword.equals("")) 
				&& !(newPassword.equals(confirmNewPassword))) {
			bindingResult.rejectValue("confirmNewPassword", "user.add.password2.nomatch");
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("message", messageProfileNotUpdated);
		    model.addAttribute("alertClass", "alert-danger");
			model.addAttribute("userProfileForm", userProfileForm);
			return "user_profile";
		}
		
		String oldEmail = user.getEmail();
		user.setUsername(userProfileForm.getUsername());
		user.setDisplayName(userProfileForm.getDisplayName());
		user.setEmail(userProfileForm.getEmail());
		String encryptedPassword = userUtils.encodePassword(newPassword);					
		user.setPassword(encryptedPassword);
		
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			model.addAttribute("message", messageProfileNotUpdated);
		    model.addAttribute("alertClass", "alert-danger");
			return "user_profile";
		}	
		
		if(!oldEmail.equals(userProfileForm.getEmail())) {
			userService.deactivateUser(userId);
			userService.resendActivationLink(userId);
		}
		
		redirectAttributes.addFlashAttribute("message", messageProfileUpdated);
	    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		userService.updateUser(user);
		return "redirect:/profile";
	}
}
