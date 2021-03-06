package net.babuszka.osp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "firefighter_trainings")
@Getter
@Setter
public class FirefighterTraining {
	
	@Autowired
	public FirefighterTraining() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "firefighter_id", referencedColumnName = "id")
	private Firefighter firefighterId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "training_name", referencedColumnName = "id")
	private Training training;
	
	@Column(name = "obtain_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate obtainDate;
	
	@Column(name = "is_expiring")
	private Boolean isExpiring;
	
	@Column(name = "valid_until")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate validUntil;

	@Column(name = "certificate_number")
	private String certificateNumber;
	
	@Column(name = "issued_by")
	private String issuedBy;

	@Override
	public String toString() {
		return "FirefighterTraining [id=" + id + ", firefighterId=" + firefighterId + ", training=" + training
				+ ", obtainDate=" + obtainDate + ", isExpiring=" + isExpiring + ", validUntil=" + validUntil
				+ ", certificateNumber=" + certificateNumber + ", issuedBy=" + issuedBy + "]";
	}
	
	

}
