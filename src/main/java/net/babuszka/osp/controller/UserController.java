package net.babuszka.osp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.babuszka.osp.model.User;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.UserService;

@Controller
public class UserController {

	private UserService userService;
	private FirefighterService firefighterService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setFirefighterService(FirefighterService firefighterService) {
		this.firefighterService = firefighterService;
	}
	
	// Display all users
	@RequestMapping(path = "/manage/users", method = RequestMethod.GET)
	public String getAllFirefighters(Model model) {
		model.addAttribute("page_title", "Lista użytkowników");
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("user", new User());
		model.addAttribute("firefighters", firefighterService.getFirefightersWithNoAccount());
		return "users_list";
	}
}
