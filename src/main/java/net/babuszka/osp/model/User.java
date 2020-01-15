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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
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
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<UserRole> userRoles;
		
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	private  UserStatus status;
	
	@OneToOne
	@JoinColumn(name = "firefighter_id", referencedColumnName = "id")
	@JsonBackReference
	private Firefighter firefighter;
	
	@Override
	public String toString() {
		return displayName + " (" + username + ")";
	}
	
}
