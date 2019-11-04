package net.babuszka.osp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@PropertySource(value = "classpath:config/application.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:config/appconfig.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:config/ValidationMessages.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:config/EmailMessages.properties", encoding = "UTF-8")

public class ExternalPropertiesFilesConfig {

	// Custom validation messages from external file
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:config/ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	// Custom Email messages from external file
	@Bean
	public MessageSource emailMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:config/EmailMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource);
	    return bean;
	}
	    
}
