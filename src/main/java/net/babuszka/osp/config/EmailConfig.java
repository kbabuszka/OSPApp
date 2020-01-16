package net.babuszka.osp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import net.babuszka.osp.service.SettingsService;

@Configuration
public class EmailConfig {
	
	private static final String SETTING_NAME_EMAIL_HOST = "MAIL_HOST";
	private static final String SETTING_NAME_EMAIL_PORT = "MAIL_PORT";
	private static final String SETTING_NAME_EMAIL_USER = "MAIL_USER";
	private static final String SETTING_NAME_EMAIL_PASSWORD = "MAIL_PASSWORD";
	
	private SettingsService settingsService;
	
	@Autowired
	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
	@Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        String host = settingsService.getByName(SETTING_NAME_EMAIL_HOST).getValue();
        String port = settingsService.getByName(SETTING_NAME_EMAIL_PORT).getValue();
        Integer portInt = Integer.parseInt(port);
        String user = settingsService.getByName(SETTING_NAME_EMAIL_USER).getValue();
        String password = settingsService.getByName(SETTING_NAME_EMAIL_PASSWORD).getValue();
        Properties properties = emailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
		emailSender.setHost(host);
		emailSender.setPort(portInt);
		emailSender.setUsername(user);
		emailSender.setPassword(password);
		emailSender.setJavaMailProperties(properties);
		emailSender.setDefaultEncoding("UTF-8");
        return emailSender;
    }

}
