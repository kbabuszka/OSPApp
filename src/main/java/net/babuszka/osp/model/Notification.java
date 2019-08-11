package net.babuszka.osp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import net.babuszka.osp.utils.NotificationType;

@Entity
@Table(name = "notifications")
public class Notification {
	
	public Notification() {
		super();
	}
	
	public Notification(NotificationType type, String message) {
		this.text = message;
		this.notificationType = type;
		this.date = LocalDateTime.now();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "notification_type")
	private NotificationType notificationType;
	
	@Column(name = "notification_text")
	private String text;
	
	@Column(name = "notification_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")
	private LocalDateTime date;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "is_read")
	private boolean isRead;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	
}
