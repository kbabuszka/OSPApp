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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import net.babuszka.osp.utils.NotificationType;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {
	
	@Autowired
	public Notification() {
		super();
	}

	public Notification(NotificationType notificationType, String text) {
		super();
		this.notificationType = notificationType;
		this.text = text;
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
	private Boolean isRead;

}
