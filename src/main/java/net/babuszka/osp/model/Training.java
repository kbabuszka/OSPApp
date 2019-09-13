package net.babuszka.osp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name = "training_names")
public class Training {

	@Autowired
	public Training() {
		super();
	}

	public Training(
			@Size(min = 3, message = "{training.name.size}") @NotEmpty(message = "{training.name.empty}") String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@Size(min = 3, message = "{training.name.size}")
	@NotEmpty(message = "{training.name.empty}")
	private String name;

	@OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
	private List<FirefighterTraining> trainings;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FirefighterTraining> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<FirefighterTraining> trainings) {
		this.trainings = trainings;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", name=" + name + "]";
	}

}
