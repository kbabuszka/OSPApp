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

@Entity
@Table(name = "firefighter_trainings")
public class FirefighterTraining {
	
	@Autowired
	public FirefighterTraining() {
		super();
	}

	public FirefighterTraining(Firefighter firefighterId, Training training) {
		super();
		this.firefighterId = firefighterId;
		this.training = training;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "firefighter_id", referencedColumnName = "id")
	private Firefighter firefighterId;
	
	@ManyToOne
	@JoinColumn(name = "training_name", referencedColumnName = "id")
	private Training training;
	
	@Column(name = "obtain_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate obtainDate;
	
	@Column(name = "is_expiring")
	private boolean isExpiring;
	
	@Column(name = "valid_until")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate validUntil;

	@Column(name = "certificate_number")
	private String certificateNumber;
	
	@Column(name = "issued_by")
	private String issuedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Firefighter getFirefighterId() {
		return firefighterId;
	}

	public void setFirefighterId(Firefighter firefighterId) {
		this.firefighterId = firefighterId;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public LocalDate getObtainDate() {
		return obtainDate;
	}

	public void setObtainDate(LocalDate obtainDate) {
		this.obtainDate = obtainDate;
	}

	public boolean getIsExpiring() {
		return isExpiring;
	}

	public void setIsExpiring(boolean isExpiring) {
		this.isExpiring = isExpiring;
	}

	public LocalDate getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(LocalDate validUntil) {
		this.validUntil = validUntil;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	@Override
	public String toString() {
		return "FirefighterTraining [id=" + id + ", firefighterId=" + firefighterId + ", training=" + training
				+ ", obtainDate=" + obtainDate + ", isExpiring=" + isExpiring + ", validUntil=" + validUntil
				+ ", certificateNumber=" + certificateNumber + ", issuedBy=" + issuedBy + "]";
	}
	
	

}
