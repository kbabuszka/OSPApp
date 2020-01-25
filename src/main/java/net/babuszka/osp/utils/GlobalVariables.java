package net.babuszka.osp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.babuszka.osp.service.SettingsService;

@ControllerAdvice
@Component
public class GlobalVariables {
	
	private static final String SETTING_NAME_DEPARTMENT_NAME = "DEPARTMENT_NAME";
	private static final String SETTING_NAME_DEPARTMENT_NAME_PREFIX = "DEPARTMENT_NAME_PREFIX";
	private static final String SETTING_NAME_DEPARTMENT_FULL_NAME = "DEPARTMENT_FULL_NAME";	
	private static final String SETTING_NAME_DEPARTMENT_ADDRESS_STREET = "DEPARTMENT_ADDRESS_STREET";
	private static final String SETTING_NAME_DEPARTMENT_ADDRESS_CITY = "DEPARTMENT_ADDRESS_CITY";
	private static final String SETTING_NAME_DEPARTMENT_ADDRESS_POSTAL_CODE = "DEPARTMENT_ADDRESS_POSTAL_CODE";
	private static final String SETTING_NAME_DEPARTMENT_APP_URL = "DEPARTMENT_APP_URL";

	private static final String APPLICATION_NAME = "OSP Manager";
	private static final String APPLICATION_VERSION = "1.0.4";
	private static final String APPLICATION_WEBSITE = "ospapp.pl";
	private static final String APPLICATION_AUTHOR = "Kamil Babuszka";
	private static final String APPLICATION_AUTHOR_WEBSITE = "http://babuszka.net";
	
	private SettingsService settingsService;
	
	public GlobalVariables() {
		super();
	}
	
	@Autowired
	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
	// Department variables
	@ModelAttribute("global_config_title")
	public String getPageTitle() {
		return getDepartmentNamePrefix() + " " + getDepartmentName();
	}
	
	@ModelAttribute("global_department_name_prefix")
	public String getDepartmentNamePrefix() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_NAME_PREFIX).getValue();
	}
	
	@ModelAttribute("global_department_name")
	public String getDepartmentName() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_NAME).getValue();
	}
	
	@ModelAttribute("global_department_full_name")
	public String getFullDepartmentName() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_FULL_NAME).getValue();
	}
	
	@ModelAttribute("global_department_address_street")
	public String getDepartmentAddressStreet() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_ADDRESS_STREET).getValue();
	}
	
	@ModelAttribute("global_department_address_city")
	public String getDepartmentAddressCity() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_ADDRESS_CITY).getValue();
	}
	
	@ModelAttribute("global_department_address_postal_code")
	public String getDepartmentAddressPostalcode() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_ADDRESS_POSTAL_CODE).getValue();
	}
	
	@ModelAttribute("global_application_url")
	public String getDepartmentAppUrl() {
		return settingsService.getByName(SETTING_NAME_DEPARTMENT_APP_URL).getValue();
	}
	
	
	// App variables
	@ModelAttribute("global_application_name")
	public String getAppName() {
		return APPLICATION_NAME;
	}
	
	@ModelAttribute("global_application_version")
	public String getAppVersion() {
		return APPLICATION_VERSION;
	}
	
	@ModelAttribute("global_application_website")
	public String getAppWebsite() {
		return APPLICATION_WEBSITE;
	}
	
	@ModelAttribute("global_application_author")
	public String getAppAuthor() {
		return APPLICATION_AUTHOR;
	}
	
	@ModelAttribute("global_application_author_website")
	public String getAppAuthorWebsite() {
		return APPLICATION_AUTHOR_WEBSITE;
	}
	
	// Email settings
	

	
}
