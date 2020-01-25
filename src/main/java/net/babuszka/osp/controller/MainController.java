package net.babuszka.osp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.babuszka.osp.model.FirefighterAvailability;
import net.babuszka.osp.service.FirefighterAvailabilityService;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.UserService;

@Controller
public class MainController {
	
	private UserService userService;
	private FirefighterService firefighterService;
	private FirefighterAvailabilityService firefighterAvailabilityService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setFirefighterService(FirefighterService firefighterService) {
		this.firefighterService = firefighterService;
	}
	
	@Autowired
	public void setFirefighterAvailabilityService(FirefighterAvailabilityService firefighterAvailabilityService) {
		this.firefighterAvailabilityService = firefighterAvailabilityService;
	}

	@GetMapping(path = "/")
	public String initDashboardView(Model model) {
		model.addAttribute("page_title", "Pulpit");
		//Infoboxes
		Integer firefightersAmount = firefighterService.getAllFirefighters().size();
		model.addAttribute("infobox_firefighters_total_amount", firefightersAmount);
		Integer availableFirefightersCount = firefighterAvailabilityService.getAvailableFirefightersCount();
		model.addAttribute("infobox_firefighters_available", availableFirefightersCount);
		
		// Cards
		List<FirefighterAvailability> latestStatusChanges = firefighterAvailabilityService.getLatestStatusChanges(8);
		model.addAttribute("card_latest_status_changes", latestStatusChanges);
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
	
	@GetMapping(path = "/changelog")
	public String initChangelogView(Model model) {
		model.addAttribute("page_title", "Changelog");
		return "changelog";
	}
}
