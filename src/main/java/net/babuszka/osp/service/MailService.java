package net.babuszka.osp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

	@Value("${spring.application.name}")
	private String applicationName;
	
	private Logger LOG = LoggerFactory.getLogger(Logger.class);
	private JavaMailSender emailSender;
	private TemplateEngine templateEngine;
	 
	@Autowired
	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	 
	@Autowired
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void sendSimpleMessage(String to, String subject, String message) throws Exception {
		 SimpleMailMessage mailMessage = new SimpleMailMessage();
		 mailMessage.setTo(to);
		 mailMessage.setSubject(subject); 
		 mailMessage.setText(message);
	     try {
	    	 emailSender.send(mailMessage);
	     } catch (Exception e) {
			LOG.debug("E-mail nie został wysłany: " + e.toString());
		 }
	}
		
	public void sendHtmlMessage(String to, String subject, String template, Context context) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(to);
	        messageHelper.setSubject(subject);
	        messageHelper.setFrom("no-reply@ospapp.pl", applicationName);
	        String content = templateEngine.process("email/"+template, context);
	        messageHelper.setText(content, true);
	    };
	    
	    try {
	    	emailSender.send(messagePreparator);
	    } catch (Exception e) {
	    	LOG.debug("E-mail was not sent: " + e.toString());
		}
	}
	
}
