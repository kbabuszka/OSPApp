package net.babuszka.osp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "firefighter_availability")
@Getter
@Setter
public class FirefighterAvailability {

	@Autowired
	public FirefighterAvailability() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "firefighter_id", referencedColumnName = "id")
	private Firefighter firefighter;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "availability")
	private FirefighterAvailabilityType type;
	
	@Column(name = "valid_from")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime validFrom;
	
	@Column(name = "valid_before")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime validBefore;
}
