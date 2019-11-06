package net.babuszka.osp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalVariables {
	
	@Value("${app.config.department.name}")
	private String departmentName;

	@Value("${app.config.department.name.prefix}")
	private String departmentNamePrefix;

	@Value("${app.config.department.name.full}")
	private String departmentFullName;
	
	@Value("${app.config.url}")
	private String applicationUrl;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${spring.application.version}")
	private String applicationVersion;
	
	@Value("${spring.application.author}")
	private String applicationAuthor;
	
	@Value("${spring.application.author.website}")
	private String applicationAuthorWebsite;
	
	public GlobalVariables() {
		super();
	}
	
	@ModelAttribute("global_config_title")
	public String getPageTitle() {
		return departmentNamePrefix + " " + departmentName;
	}
	
	@ModelAttribute("global_department_name_prefix")
	public String getDepartmentNamePrefix() {
		return departmentNamePrefix;
	}
	
	@ModelAttribute("global_department_name")
	public String getDepartmentName() {
		return departmentName;
	}
	
	@ModelAttribute("global_department_full_name")
	public String getFullDepartmentName() {
		return departmentFullName;
	}
	
	@ModelAttribute("global_application_url")
	public String getAppUrl() {
		return applicationUrl;
	}
	
	@ModelAttribute("global_application_name")
	public String getAppName() {
		return applicationName;
	}
	
	@ModelAttribute("global_application_version")
	public String getAppVersion() {
		return applicationVersion;
	}
	
	@ModelAttribute("global_application_author")
	public String getAppAuthor() {
		return applicationAuthor;
	}
	
	@ModelAttribute("global_application_author_website")
	public String getAppAuthorWebsite() {
		return applicationAuthorWebsite;
	}
	
}
