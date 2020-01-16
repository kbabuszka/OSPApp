package net.babuszka.osp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import net.babuszka.osp.utils.GlobalVariables;

@Service
public class MailService {

	private Logger LOG = LoggerFactory.getLogger(Logger.class);
	private JavaMailSenderImpl emailSender;
	private TemplateEngine templateEngine;
	private GlobalVariables globalVariables;
	 
	@Autowired
	public void setEmailSender(JavaMailSenderImpl emailSender) {
		this.emailSender = emailSender;
	}
	 
	@Autowired
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	@Autowired
	public void setGlobalVariables(GlobalVariables globalVariables) {
		this.globalVariables = globalVariables;
	}

	public MailService() {
		super();
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
		String applicationName = globalVariables.getAppName();
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
