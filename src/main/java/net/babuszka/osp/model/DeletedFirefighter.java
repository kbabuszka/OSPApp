package net.babuszka.osp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "firefighters_deleted")
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
	
	@ManyToOne
	@JoinColumn(name = "deleted_by", referencedColumnName = "id")
	private User deletedByUser;

	@Autowired
	public DeletedFirefighter() {
		super();
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(LocalDateTime deletionDate) {
		this.deletionDate = deletionDate;
	}

	public User getDeletedByUser() {
		return deletedByUser;
	}

	public void setDeletedByUser(User deletedByUser) {
		this.deletedByUser = deletedByUser;
	}
	
	@Override
	public String toString() {
		return "Firefighter [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
