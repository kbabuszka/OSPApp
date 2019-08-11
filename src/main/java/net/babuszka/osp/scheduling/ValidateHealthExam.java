package net.babuszka.osp.scheduling;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.Notification;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.NotificationService;
import net.babuszka.osp.utils.NotificationType;


@Component
public class ValidateHealthExam {
	
	private Logger LOG = LoggerFactory.getLogger(ValidateHealthExam.class);
	private FirefighterService firefighterService;
	private NotificationService notificationService;
	
	@Autowired
	public void setFirefighterService(FirefighterService firefighterService) {
		this.firefighterService = firefighterService;
	}
	
	@Autowired
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Scheduled(cron = "0 0 8 * * *")
	public void Validate() {
		LocalDate now = LocalDate.now();
		LocalDate dueDate;
		List<Firefighter> firefighters = firefighterService.getJotFirefighters(); 
		for(Firefighter firefighter : firefighters) {
			dueDate = firefighter.getHealthExamDate();
			if(dueDate != null) {
				if(dueDate.isBefore(now)) {
					String message = firefighter.getFirstName() + " " + firefighter.getLastName() + " nie ma aktualnych badań lekarskich!";
					LOG.info(message);
					Notification notification = new Notification(NotificationType.DANGER, message);
					notificationService.addNotification(notification);
				}
				if((dueDate.getDayOfYear() - now.getDayOfYear()) < 30) {
					String message = firefighter.getFirstName() + " " + firefighter.getLastName() + " nie ma aktualnych badań lekarskich!";
					LOG.info(message);
					Notification notification = new Notification(NotificationType.DANGER, message);
					notificationService.addNotification(notification);
				}
			} else {
				String message = "Zbliża się termin badań lekarskich strażaka: " + firefighter.getFirstName() + " " + firefighter.getLastName();
				LOG.info(message);
				Notification notification = new Notification(NotificationType.WARNING, message);
				notificationService.addNotification(notification);
			}

		}
	}
	
}
