package net.babuszka.osp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(path = "/")
	public String dashboard() {
		return "index";
	}
	
	@RequestMapping(path = "/login")
	public String login() {
		return "login";
	}
}
