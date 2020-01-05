package net.babuszka.osp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "users")
public class User {
	
	@Autowired
	public User() {
		super();
		this.status = UserStatus.INACTIVE;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username", nullable = false, unique = true)
	@NotEmpty(message = "{user.username.empty}")
	private String username;
	
	@Column(name = "display_name")
	@Size(min = 3, message = "{user.displayname.size}")
	private String displayName;
	
	@Column(name = "email")
	@Email(message = "{user.email}")
	@NotEmpty(message = "{user.email.empty}")
	private String email;
	
	@Column(name = "password")
	@NotEmpty(message = "{user.password.empty}")
	private String password;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OneToMany(mappedBy = "user")
	private List<UserRole> userRoles;
		
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	private  UserStatus status;
	
	@OneToOne
	@JoinColumn(name = "firefighter_id", referencedColumnName = "id")
	private Firefighter firefighter;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> roles) {
		this.userRoles = roles;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Firefighter getFirefighter() {
		return firefighter;
	}

	public void setFirefighter(Firefighter firefighter) {
		this.firefighter = firefighter;
	}
	
	@Override
	public String toString() {
		return displayName + " (" + username + ")";
	}
	
}
