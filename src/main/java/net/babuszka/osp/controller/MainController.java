package net.babuszka.osp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(path = "/")
	public String dashboard(Model model) {
		model.addAttribute("page_title", "Pulpit");
		return "index";
	}
	
	@RequestMapping(path = "/login")
	public String login(Model model) {
		model.addAttribute("page_title", "Logowanie");
		return "login";
	}
}
