package net.babuszka.osp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.NewUserForm;
import net.babuszka.osp.model.User;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.UserService;
import net.babuszka.osp.utils.UserUtils;

@Controller
public class UserController {

	@Value("${user.add.message.success}")
	private String messageUserAdded;
	
	@Value("${user.add.message.error}")
	private String messageUserNotAdded;
		
	private UserService userService;
	private FirefighterService firefighterService;
	private UserUtils userUtils;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setFirefighterService(FirefighterService firefighterService) {
		this.firefighterService = firefighterService;
	}
	
	@Autowired
	public void setUserUtils(UserUtils userUtils) {
		this.userUtils = userUtils;
	}

	// Display all users
	@RequestMapping(path = "/manage/users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		model.addAttribute("page_title", "Zarządzaj użytkownikami");
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("user", new User());
		model.addAttribute("user_form", new NewUserForm());
		model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		return "users_list";
	}
	
	//Submit new user form
	@PostMapping(path = "/manage/users/add")
	public String addNewUser(@ModelAttribute("user_form") @Valid NewUserForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("page_title", "Zarządzaj użytkownikami");
		User newUser = userForm.getUser();
		
		if(!(newUser.getPassword().equals(userForm.getConfirmPassword())))
			bindingResult.rejectValue("confirmPassword", "user.add.password2.nomatch");
		if(userService.findUserByEmail(newUser.getEmail()) != null)
			bindingResult.rejectValue("user.email", "user.email.duplicate");
		if(userService.findUserByUsername(newUser.getUsername()) != null)
			bindingResult.rejectValue("user.username", "user.username.duplicate");
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("message", messageUserNotAdded);
		    model.addAttribute("alertClass", "alert-danger");
		    model.addAttribute("users", userService.getAllUsers());
		    model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		    return "users_list";
		} else {
			String encryptedPassword = userUtils.encodePassword(newUser.getPassword());
			newUser.setPassword(encryptedPassword);
			userService.saveUser(newUser);
			redirectAttributes.addFlashAttribute("message", messageUserAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/users";
		} 
	}
}
