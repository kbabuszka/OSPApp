package net.babuszka.osp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "firefighters")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Firefighter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "firstname")
	@Size(min = 3, max = 30, message = "{firefighter.firstname.size}")
	@NotEmpty(message = "{firefighter.firstname.empty}")
	private String firstName;

	@Column(name = "secondname")
	private String secondName;

	@Column(name = "lastname")
	@Size(min = 3, max = 30, message = "{firefighter.lastname.size}")
	@NotEmpty(message = "{firefighter.lastname.empty}")
	private String lastName;

	@Column(name = "gender")
	@NotEmpty(message = "{firefighter.gender.empty}")
	private String gender;

	@Column(name = "birthdate")
	@Past(message = "{firefighter.birthdate.past}")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthDate;

	@Column(name = "birthplace")
	private String birthPlace;

	@Column(name = "pesel", unique = true)
	@Pattern(regexp = "[0-9]{11}", message = "{firefighter.pesel.pattern}")
	private String pesel;

	@Column(name = "address_street")
	private String addressStreet;

	@Column(name = "address_1")
	private String address_1;

	@Column(name = "address_2")
	private String address_2;

	@Column(name = "address_city")
	private String addressCity;

	@Column(name = "address_postal_code")
	@Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "{firefighter.postalcode.pattern}")
	private String addressPostalCode;

	@Column(name = "join_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@PastOrPresent(message = "{firefighter.joindate.pastorpresent}")
	private LocalDate joinDate;

	@Column(name = "email")
	@Email(message = "{firefighter.email}")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "in_jot")
	private Boolean isInJOT;
	
	@Column(name = "health_exam_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate healthExamDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "type", referencedColumnName = "id")
	@NotNull(message = "{firefighter.type}")
	private FirefighterType type;

	@OneToMany(mappedBy = "firefighterId")
	private List<FirefighterTraining> trainings;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Autowired
	public Firefighter() {
		super();
		this.trainings = new ArrayList<FirefighterTraining>();
	}

	public void setType(FirefighterType type) throws IllegalArgumentException {
		if (type instanceof FirefighterType) {
			this.type = type;
		} else {
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public String toString() {
		return "Firefighter [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
