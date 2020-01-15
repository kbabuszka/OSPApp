package net.babuszka.osp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "firefighters_deleted")
@Getter
@Setter
public class DeletedFirefighter {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "firstname")
	@NotEmpty
	private String firstName;

	@Column(name = "lastname")
	@NotEmpty
	private String lastName;
	
	@Column(name = "deletion_date")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime deletionDate;
	
	@Column(name = "deleted_by")
	private String deletedByUser;

	@Autowired
	public DeletedFirefighter() {
		super();
	}
	
	@Override
	public String toString() {
		return "Deleted Firefighter [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
