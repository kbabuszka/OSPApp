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

@Entity
@Table(name = "firefighters")
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
	
	@Autowired
	public Firefighter() {
		super();
		this.trainings = new ArrayList<FirefighterTraining>();
	}
	
	public Firefighter(
			@Size(min = 3, max = 30, message = "{firefighter.firstname.size}") @NotEmpty(message = "{firefighter.firstname.empty}") String firstName,
			@Size(min = 3, max = 30, message = "{firefighter.lastname.size}") @NotEmpty(message = "{firefighter.lastname.empty}") String lastName,
			@NotEmpty(message = "{firefighter.gender.empty}") String gender,
			@NotNull(message = "{firefighter.type}") FirefighterType type) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.type = type;
		this.trainings = new ArrayList<FirefighterTraining>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddress_1() {
		return address_1;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressPostalCode() {
		return addressPostalCode;
	}

	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsInJOT() {
		return isInJOT;
	}

	public void setIsInJOT(Boolean isInJOT) {
		this.isInJOT = isInJOT;
	}

	public LocalDate getHealthExamDate() {
		return healthExamDate;
	}

	public void setHealthExamDate(LocalDate healthExamDate) {
		this.healthExamDate = healthExamDate;
	}

	public FirefighterType getType() {
		return type;
	}

	public void setType(FirefighterType type) throws IllegalArgumentException {
		if (type instanceof FirefighterType) {
			this.type = type;
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	public List<FirefighterTraining> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<FirefighterTraining> trainings) {
		this.trainings = trainings;
	}
	
	public void addTraining(FirefighterTraining firefighterTraining) {
		this.trainings.add(firefighterTraining);
	}

	@Override
	public String toString() {
		return "Firefighter [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
