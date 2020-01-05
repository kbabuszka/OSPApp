package net.babuszka.osp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.babuszka.osp.service.UserService;

@Controller
public class MainController {
	
	UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	@GetMapping(path = "/")
	public String initDashboardView(Model model) {
		model.addAttribute("page_title", "Pulpit");
		return "index";
	}
	
	@GetMapping(path = "/login")
	public String initLoginForm(Model model) {
		model.addAttribute("page_title", "Logowanie");
		return "login";
	}
	
	@GetMapping(path = "/activate-account/{token}")
	public String initActivateAccountView(Model model, @PathVariable(name = "token") String token) {
		model.addAttribute("page_title", "Potwierdź adres e-mail");
		model.addAttribute("activated", userService.verifyUserEmail(token));
		return "activate_account";
	}
}
