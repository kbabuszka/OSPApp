package net.babuszka.osp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserForm;
import net.babuszka.osp.model.UserStatus;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.RoleService;
import net.babuszka.osp.service.UserService;
import net.babuszka.osp.utils.UserUtils;

@Controller
public class UserController {

	@Value("${user.add.message.success}")
	private String messageUserAdded;
	
	@Value("${user.add.message.error}")
	private String messageUserNotAdded;
	
	@Value("${user.edit.message.success}")
	private String messageUserEdited;
	
	@Value("${user.edit.message.error}")
	private String messageUserNotEdited;
	
	@Value("${user.delete.message.success}")
	private String messageUserDeleted;
	
	@Value("${user.delete.message.error}")
	private String messageUserNotDeleted;	
	
	@Value("${user.activationlink.user.active}")
	private String messageUserIsActive;
	
	@Value("${user.message.user.notexist}")
	private String messageUserNotExist;
	
	@Value("${user.activationlink.resent}")
	private String messageLinkResent;
	
	@Value("${user.deactivate.success}")
	private String messageUserDeactivated;
	
	@Value("${user.deactivate.error}")
	private String messageUserNotDeactivated;
		
	private UserService userService;
	private FirefighterService firefighterService;
	private RoleService roleService;
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
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setUserUtils(UserUtils userUtils) {
		this.userUtils = userUtils;
	}

	// Display all users
	@GetMapping(path = "/manage/users")
	public String getAllUsers(Model model) {
		model.addAttribute("page_title", "Zarządzaj użytkownikami");
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		model.addAttribute("roles", roleService.getAllRoles());
		return "users_list";
	}
	
	//Submit new user form
	@PostMapping(path = "/manage/users/add")
	public String addNewUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("page_title", "Zarządzaj użytkownikami");
		
		if(userForm.getEmail().isEmpty())
			bindingResult.rejectValue("email", "user.email.empty");
		if(userForm.getPassword().isEmpty())
			bindingResult.rejectValue("password", "user.password.empty");
		if(!(userForm.getPassword().equals(userForm.getConfirmPassword())))
			bindingResult.rejectValue("confirmPassword", "user.add.password2.nomatch");
		if(userService.findUserByEmail(userForm.getEmail()) != null)
			bindingResult.rejectValue("email", "user.email.duplicate");
		if(userService.findUserByUsername(userForm.getUsername()) != null)
			bindingResult.rejectValue("username", "user.username.duplicate");
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("message", messageUserNotAdded);
		    model.addAttribute("alertClass", "alert-danger");
		    model.addAttribute("users", userService.getAllUsers());
		    model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		    return "users_list";
		} else {
			User newUser = new User();
			newUser.setUsername(userForm.getUsername());
			newUser.setDisplayName(userForm.getDisplayName());
			newUser.setEmail(userForm.getEmail());
			String encryptedPassword = userUtils.encodePassword(userForm.getPassword());
			newUser.setPassword(encryptedPassword);
			userService.addNewUser(newUser);
			redirectAttributes.addFlashAttribute("message", messageUserAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/users";
		} 
	}
	
	//Display edit form
	@GetMapping(path = "/manage/users/edit/{id}")
	public String editUser(Model model, @PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {	
		User user = userService.getUser(id);
		if(user == null) {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";
		}
		model.addAttribute("page_title", "Edytuj użytkownika");
		model.addAttribute("userForm", new UserForm(user));
		model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		model.addAttribute("roles", roleService.getAllRoles());
		return "user_edit";
	}
	
	//Submit edit form
	@PostMapping(path = "/manage/users/edit/{id}")
	public String saveUser(@ModelAttribute("userForm") @Valid UserForm userForm, @PathVariable(name = "id") Integer id, 
			Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		
		model.addAttribute("page_title", "Edytuj użytkownika");
		User user = userService.getUser(userForm.getId());
		if(user == null) {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";		
		}
		
		if(userForm.getEmail().isEmpty())
			bindingResult.rejectValue("email", "user.email.empty");
		
		if(!(userForm.getPassword().equals("")) 
				&& !(userForm.getPassword().equals(userForm.getConfirmPassword()))) {
			bindingResult.rejectValue("confirmPassword", "user.add.password2.nomatch");
		}			

		if(userService.findUserByEmail(userForm.getEmail()) != null
				&& userService.findUserByEmail(userForm.getEmail()) != user)
			bindingResult.rejectValue("email", "user.email.duplicate");
			
		if(userService.findUserByUsername(userForm.getUsername()) != null
				&& userService.findUserByUsername(userForm.getUsername()) != user)
			bindingResult.rejectValue("username", "user.username.duplicate");
			
		if(bindingResult.hasErrors()) {
			model.addAttribute("message", messageUserNotEdited);
		    model.addAttribute("alertClass", "alert-danger");
			model.addAttribute("userForm", userForm);
			model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
			model.addAttribute("roles", roleService.getAllRoles());
			return "user_edit";
		} 
		
		if(!user.getEmail().equals(userForm.getEmail())) {
			userService.deactivateUser(id);
			userService.resendActivationLink(id);
		}	
		user.setUsername(userForm.getUsername());
		user.setDisplayName(userForm.getDisplayName());
		user.setEmail(userForm.getEmail());
		String encryptedPassword = userUtils.encodePassword(userForm.getPassword());					
		user.setPassword(encryptedPassword);
		userService.saveUser(user);
		redirectAttributes.addFlashAttribute("message", messageUserEdited);
	    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/manage/users";	
	}
	
	//Resend activation link
	@GetMapping(path = "/manage/users/resend-link/{id}")
	public String resendActivationLink(Model model, @PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		User user = userService.getUser(id);
		if(user == null) {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		    return "redirect:/manage/users";
		}
		    
		if(user.getStatus() == UserStatus.INACTIVE) {
			userService.resendActivationLink(id);
			redirectAttributes.addFlashAttribute("message", messageLinkResent);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserIsActive);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/manage/users";
	}
	
	// Deactivate user
	@GetMapping(path = "/manage/users/deactivate/{id}")
	public String deactivateUser(@PathVariable (name = "id") Integer id, RedirectAttributes redirectAttributes) {
		if(userService.deactivateUser(id)) {
			redirectAttributes.addFlashAttribute("message", messageUserDeactivated);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserNotDeactivated);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/manage/users";
	}
	
	// Delete user
	@GetMapping(path = "/manage/users/delete/{id}")
	public String deleteUser(Model model, @PathVariable (name = "id") Integer id, RedirectAttributes redirectAttributes) {
		User user = userService.getUser(id);
		if (user == null) {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";
		}
		userService.deleteUser(id);
		if(userService.getUser(id) == null) {
			redirectAttributes.addFlashAttribute("message", messageUserDeleted);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/users";
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserNotDeleted);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";
		}
	}
}
