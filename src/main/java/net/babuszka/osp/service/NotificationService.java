package net.babuszka.osp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Notification;
import net.babuszka.osp.repository.NotificationRepository;

@Service
public class NotificationService {
	
	private Logger LOG = LoggerFactory.getLogger(NotificationService.class);
	
	private NotificationRepository notificationRepository;

	@Autowired
	public void setNotificationRepository(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	public List<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}
	
	public void addNotification(Notification notification) {
		LOG.info("Adding new notification into database: " + notification.getText() + " " + notification.getNotificationType());
		notificationRepository.save(notification);
	}
	
	public void markAsRead(Integer id) {
		notificationRepository.markAsRead(id);
	}

}
