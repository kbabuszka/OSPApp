package net.babuszka.osp.utils;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	 private Logger LOG = LoggerFactory.getLogger(Logger.class);
	
	 @Autowired
	 public JavaMailSender emailSender;
	 
	 public void sendSimpleMessage(String to, String subject, String content) throws Exception {
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setTo(to);
	     message.setSubject(subject); 
	     message.setText(content);
	     emailSender.send(message);
	 }
	 
}
