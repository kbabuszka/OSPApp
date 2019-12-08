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

import net.babuszka.osp.model.NewUserForm;
import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserStatus;
<<<<<<< HEAD
import net.babuszka.osp.repository.UserRepository;
=======
>>>>>>> temp
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
		model.addAttribute("user", new User());
		model.addAttribute("user_form", new NewUserForm());
		model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		model.addAttribute("roles", roleService.getAllRoles());
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
			userService.addNewUser(newUser);
			redirectAttributes.addFlashAttribute("message", messageUserAdded);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/manage/users";
		} 
	}
	
	//Display edit form
	@GetMapping(path = "/manage/users/edit/{id}")
	public String editUser(Model model, @PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		model.addAttribute("page_title", "Edytuj użytkownika");
		if(userService.getUser(id) != null) {
			model.addAttribute("user", userService.getUser(id));
			model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
			model.addAttribute("roles", roleService.getAllRoles());
			return "users_edit";
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";
		}
	}
	
	//Resend activation link
	@GetMapping(path = "/manage/users/resend-link/{id}")
	public String resendActivationLink(Model model, @PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		User user = userService.getUser(id);
		if(user != null) {
			if(user.getStatus() == UserStatus.INACTIVE) {
				userService.resendActivationLink(id);
				redirectAttributes.addFlashAttribute("message", messageLinkResent);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", messageUserIsActive);
			    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
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
		if (user != null) {
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
		} else {
			redirectAttributes.addFlashAttribute("message", messageUserNotExist);
		    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/manage/users";
		}
	}
}
