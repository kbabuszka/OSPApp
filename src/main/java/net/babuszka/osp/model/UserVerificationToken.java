package net.babuszka.osp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "user_verification_tokens")
public class UserVerificationToken {

	private static final Long EXPIRATION_TIME = (long) 24;
	
	@Autowired
	public UserVerificationToken() {
		super();
	}

	@Autowired
	public UserVerificationToken(User user, String token) {
		super();
		this.user = user;
		this.token = token;
		this.expirationDate = calculateExpirationDate();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
		
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	private LocalDateTime calculateExpirationDate() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiration = now.plusHours(EXPIRATION_TIME);
		return expiration;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
