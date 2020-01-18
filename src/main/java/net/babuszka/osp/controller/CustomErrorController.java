package net.babuszka.osp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping(path = "/error")
    public String handleError(HttpServletResponse response) {
		Integer status = response.getStatus();
		System.out.println(status);
        if(status == HttpStatus.FORBIDDEN.value()) {
            return "error-403";
        }
        if(status == HttpStatus.NOT_FOUND.value()) {
            return "error-404";
        }
        else if(status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "error-500";
        }
        
        return "error";
    }
	
	@Override
	public String getErrorPath() {
		return null;
	}
}
